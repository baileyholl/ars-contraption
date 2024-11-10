package com.hollingsworth.ars_creo.events;

import com.hollingsworth.ars_creo.ArsCreo;
import com.hollingsworth.ars_creo.common.PotionTank;
import com.hollingsworth.arsnouveau.common.block.tile.PotionJarTile;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = ArsCreo.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {

    // add fluid handler to potion jar
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCapabilityEvent(AttachCapabilitiesEvent<BlockEntity> event) {
        if (event.getObject() instanceof PotionJarTile tile) {
            FluidTank tank = new PotionTank(tile);
            LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);

            ICapabilityProvider provider = new ICapabilityProvider() {
                @Override
                public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction direction) {
                    if (cap == ForgeCapabilities.FLUID_HANDLER) {
                        return holder.cast();
                    }
                    return LazyOptional.empty();
                }
            };

            event.addCapability(new ResourceLocation(ArsCreo.MODID, "potion_fluid_handler"), provider);
        }
    }

}
