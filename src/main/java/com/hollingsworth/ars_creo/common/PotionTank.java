package com.hollingsworth.ars_creo.common;

import com.hollingsworth.arsnouveau.api.potion.PotionData;
import com.hollingsworth.arsnouveau.common.block.tile.PotionJarTile;
import com.simibubi.create.AllFluids;
import com.simibubi.create.content.fluids.potion.PotionFluid;
import com.simibubi.create.content.fluids.potion.PotionFluidHandler;
import net.minecraft.util.Mth;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

public class PotionTank extends FluidTank {

    public static final double POTION_TO_MB = 2.5;
    public static final double MB_TO_POTION = 0.4;
    private final PotionJarTile jar;

    public PotionTank(PotionJarTile tile) {
        super((int) (tile.getMaxFill() * POTION_TO_MB), stack -> stack.getFluid().isSame(AllFluids.POTION.getSource()));
        jar = tile;
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        return super.isFluidValid(stack) && jar.canAccept(PotionData.fromTag(stack.getTag()), 1);
    }

    @NotNull
    public FluidStack getFluid() {
        return PotionFluidHandler.getFluidFromPotion(jar.getData().getPotion(), PotionFluid.BottleType.REGULAR, this.getFluidAmount());
    }

    @Override
    public boolean isEmpty() {
        return jar.getData().isEmpty();
    }

    @Override
    public int getFluidAmount() {
        return (int) (jar.getAmount() * POTION_TO_MB);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (resource.isEmpty() || !isFluidValid(resource)) {
            return 0;
        }
        PotionData data = PotionData.fromTag(resource.getTag());

        if (action.simulate()) {
            if (jar.getData().isEmpty()) {
                return Math.min(capacity, resource.getAmount());
            }
            if (!data.areSameEffects(jar.getData())) {
                return 0;
            }
            return Math.min(capacity - getFluidAmount(), resource.getAmount());
        }

        if (jar.getData().isEmpty()) {

            int amountToAdd = Mth.ceil(resource.getAmount() * MB_TO_POTION);
            if (amountToAdd > 0) {
                jar.add(data, amountToAdd);
                return resource.getAmount();
            }
            return 0;
        }

        if (!data.areSameEffects(jar.getData())) {
            return 0;
        }

        int filled = capacity - getFluidAmount(); // room left
        int amountToAdd = Mth.ceil(resource.getAmount() * MB_TO_POTION);

        if (amountToAdd > 0) {
            jar.add(data, amountToAdd);
            if (resource.getAmount() < filled) {
                filled = resource.getAmount(); // room > fluid, return amount filled
            }
        }

        return filled;
    }

    @NotNull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        int drained = maxDrain;
        if (getFluidAmount() < drained) {
            drained = getFluidAmount();
        }

        FluidStack stack = PotionFluidHandler.getFluidFromPotion(jar.getData().getPotion(), PotionFluid.BottleType.REGULAR, drained);
        if (action.execute() && drained > 0) {
            // Use the constant for conversion from drained MB to POTION
            jar.remove(Mth.ceil(drained * MB_TO_POTION));

        }
        return stack;
    }

}
