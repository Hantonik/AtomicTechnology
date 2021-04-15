package hantonik.atomictechnology.crafing.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import hantonik.atomiccore.utils.Criteria;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class RecipeBuilder<T extends RecipeBuilder<T>> {
    protected final List<ICondition> conditions = new ArrayList<>();
    protected final Advancement.Builder advancementBuilder = Advancement.Builder.builder();
    protected final ResourceLocation serializerName;

    protected RecipeBuilder(ResourceLocation serializerName) {
        this.serializerName = serializerName;
    }

    public T addCriterion(Criteria.RecipeCriterion criterion) {
        return addCriterion(criterion.name, criterion.criterion);
    }

    public T addCriterion(String name, ICriterionInstance criterion) {
        advancementBuilder.withCriterion(name, criterion);
        return (T) this;
    }

    public T addCondition(ICondition condition) {
        conditions.add(condition);
        return (T) this;
    }

    protected abstract RecipeResult getResult(ResourceLocation id);

    public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation id) {
        if (advancementBuilder.getCriteria().isEmpty())
            throw new IllegalStateException("No way of obtaining recipe " + id);

        advancementBuilder.withParentId(new ResourceLocation("recipes/root"))
                .withCriterion("has_the_recipe", new RecipeUnlockedTrigger.Instance(EntityPredicate.AndPredicate.ANY_AND, id))
                .withRewards(AdvancementRewards.Builder.recipe(id)).withRequirementsStrategy(IRequirementsStrategy.OR);
        consumer.accept(getResult(id));
    }

    protected abstract class RecipeResult implements IFinishedRecipe {
        private final ResourceLocation id;
        private final ResourceLocation advancementId;

        protected RecipeResult(ResourceLocation id) {
            this.id = id;
            this.advancementId = new ResourceLocation(id.getNamespace(), "recipes/" + id.getPath());
        }

        @Override
        public JsonObject getRecipeJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type", serializerName.toString());

            if (!conditions.isEmpty()) {
                JsonArray conditionsArray = new JsonArray();

                for (ICondition condition : conditions)
                    conditionsArray.add(CraftingHelper.serialize(condition));

                jsonObject.add("conditions", conditionsArray);
            }

            this.serialize(jsonObject);

            return jsonObject;
        }

        @Nonnull
        @Override
        public IRecipeSerializer<?> getSerializer() {
            return Objects.requireNonNull(ForgeRegistries.RECIPE_SERIALIZERS.getValue(serializerName));
        }

        @Nonnull
        @Override
        public ResourceLocation getID() {
            return this.id;
        }

        @Nullable
        @Override
        public JsonObject getAdvancementJson() {
            return advancementBuilder.serialize();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementID() {
            return this.advancementId;
        }

    }
}
