package mangopill.customized_spice_of_nostalgia.common.core;

import com.mojang.datafixers.util.Pair;
import mangopill.customized_spice_of_nostalgia.common.core.network.CSONDataHandler;
import mangopill.customized_spice_of_nostalgia.common.core.network.PlayerFoodDataPack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraftforge.network.PacketDistributor;

import java.util.*;

import static mangopill.customized_spice_of_nostalgia.common.CustomizedSpiceOfNostalgiaConfig.*;

public class PlayerFoodData extends FoodData {
    private final Queue<Pair<Integer, Float>> recentAdd;
    private final Player player;
    private float foodExpectation;
    private float saturationExpectation;

    public PlayerFoodData(Player player) {
        super();
        this.recentAdd = new LinkedList<>();
        this.player = player;
        this.foodExpectation = INITIAL_FOOD_EXPECTATION.get().floatValue();
        this.saturationExpectation = INITIAL_SATURATION_EXPECTATION.get().floatValue();
    }

    @Override
    public void eat(int foodLevel, float saturationLevel) {
        recentAdd.offer(Pair.of(foodLevel, saturationLevel));
        while (recentAdd.size() > MAX_RECENT_ADD_SIZE.get()) {
            recentAdd.poll();
        }
        ModifiedFoodData modifiedFoodData = getResult(foodLevel, foodLevel * saturationLevel * 2.0F);
        this.foodExpectation = modifiedFoodData.foodExpectation;
        this.saturationExpectation = modifiedFoodData.saturationExpectation;
        sync();
        setFoodLevel(Mth.clamp(modifiedFoodData.modifiedFoodLevel() + getFoodLevel(), 0, 20));
        setSaturation(Mth.clamp(modifiedFoodData.modifiedSaturationLevel() + getSaturationLevel(), 0.0F, (float)getFoodLevel()));
    }

    @Override
    public void addExhaustion(float exhaustion) {
        super.addExhaustion(exhaustion);
        this.foodExpectation = Mth.clamp(foodExpectation - FOOD_EXPECTATION_DECREASE_RATE.get().floatValue(), 0.0F, MAX_FOOD_EXPECTATION.get().floatValue());
        this.saturationExpectation = Mth.clamp(saturationExpectation - SATURATION_EXPECTATION_DECREASE_RATE.get().floatValue(), 0.0F, MAX_SATURATION_EXPECTATION.get().floatValue());
        sync();
    }

    public ModifiedFoodData getResult(int foodLevel, float saturationLevel) {
        sync();
        if (getFoodLevel() <= 6 && CANCEL_CALCULATION_WHEN_HUNGRY.get()) {
            return new ModifiedFoodData(foodLevel, saturationLevel, foodExpectation, saturationExpectation);
        }
        int modifiedFoodLevel = foodLevel;
        float modifiedSaturationLevel = saturationLevel;
        float foodExpectation = this.foodExpectation;
        float saturationExpectation = this.saturationExpectation;
        foodExpectation = Mth.clamp(foodExpectation + calculateComprehensiveDuplication() * modifiedFoodLevel * FOOD_EXPECTATION_GROWTH_MULTIPLIER.get().floatValue(), 0.0F, MAX_FOOD_EXPECTATION.get().floatValue());
        saturationExpectation = Mth.clamp(saturationExpectation + calculateComprehensiveDuplication() * modifiedSaturationLevel * SATURATION_EXPECTATION_GROWTH_MULTIPLIER.get().floatValue(), 0.0F, MAX_SATURATION_EXPECTATION.get().floatValue());
        if (foodLevel < foodExpectation) {
            modifiedFoodLevel = (int) (modifiedFoodLevel * (1.0F - Mth.clamp((foodExpectation - modifiedFoodLevel * calculateComprehensiveDuplication()), 0.0F, NUTRITION_REDUCTION_CAP.get().floatValue())));
        }
        if (saturationLevel < saturationExpectation) {
            modifiedSaturationLevel = modifiedSaturationLevel * (1.0F - Mth.clamp(saturationExpectation - modifiedSaturationLevel * calculateComprehensiveDuplication(), 0.0F, SATURATION_REDUCTION_CAP.get().floatValue()));
        }
        return new ModifiedFoodData(modifiedFoodLevel, modifiedSaturationLevel, foodExpectation, saturationExpectation);
    }

    public float diversityScore() {
        return 1.0F - calculateComprehensiveDuplication();
    }

    public float calculateComprehensiveDuplication() {
        if (recentAdd.isEmpty()) {
            return 0.0F;
        }
        Map<Pair<Integer, Float>, Integer> frequencyMap = new HashMap<>();
        int total = recentAdd.size();
        int uniqueCount;
        for (Pair<Integer, Float> pair : recentAdd) {
            frequencyMap.put(pair, frequencyMap.getOrDefault(pair, 0) + 1);
        }
        uniqueCount = frequencyMap.size();
        float duplicationRatio = 1.0F - (float) uniqueCount / (float) total;
        int maxFrequency = Collections.max(frequencyMap.values());
        float maxDuplication = 0.0F;
        if (total > 1) {
            maxDuplication = (float) (maxFrequency - 1) / (float) (total - 1);
        }
        float distributionScore = 0.0F;
        for (int count : frequencyMap.values()) {
            if (count > 1) {
                distributionScore += (float) count / (float) total;
            }
        }
        return 0.4F * duplicationRatio + 0.4F * maxDuplication + 0.2F * distributionScore;
    }

    public void sync() {
        if (player instanceof ServerPlayer serverPlayer) {
            CSONDataHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new PlayerFoodDataPack(recentAdd, foodExpectation, saturationExpectation));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.contains("recentAdd", 9)) {
            ListTag listTag = compoundTag.getList("recentAdd", 10);
            for (int i = 0; i < listTag.size(); i++) {
                CompoundTag pairTag = listTag.getCompound(i);
                int first = pairTag.getInt("integer");
                float second = pairTag.getFloat("float");
                recentAdd.offer(Pair.of(first, second));
            }
        }
        foodExpectation = compoundTag.getFloat("foodExpectation");
        saturationExpectation = compoundTag.getFloat("saturationExpectation");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        ListTag listTag = new ListTag();
        for (Pair<Integer, Float> pair : recentAdd) {
            CompoundTag pairTag = new CompoundTag();
            pairTag.putInt("integer", pair.getFirst());
            pairTag.putFloat("float", pair.getSecond());
            listTag.add(pairTag);
        }
        compoundTag.put("recentAdd", listTag);
        compoundTag.putFloat("foodExpectation", foodExpectation);
        compoundTag.putFloat("saturationExpectation", saturationExpectation);
    }

    public Queue<Pair<Integer, Float>> getRecentAdd() {
        return recentAdd;
    }

    public Player getPlayer() {
        return player;
    }

    public float getFoodExpectation() {
        return foodExpectation;
    }

    public float getSaturationExpectation() {
        return saturationExpectation;
    }

    public void setFoodExpectation(float foodExpectation) {
        this.foodExpectation = foodExpectation;
    }

    public void setSaturationExpectation(float saturationExpectation) {
        this.saturationExpectation = saturationExpectation;
    }

    public record ModifiedFoodData(int modifiedFoodLevel, float modifiedSaturationLevel, float foodExpectation, float saturationExpectation) {}
}
