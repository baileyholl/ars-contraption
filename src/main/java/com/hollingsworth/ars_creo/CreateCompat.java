package com.hollingsworth.ars_creo;


import com.hollingsworth.ars_creo.common.display.TurretDisplaySource;
import com.hollingsworth.ars_creo.common.lib.LibBlock;
import com.hollingsworth.ars_creo.contraption.*;
import com.hollingsworth.arsnouveau.common.lib.LibBlockNames;
import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.simibubi.create.AllInteractionBehaviours;
import com.simibubi.create.AllMovementBehaviours;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours;
import net.minecraft.resources.ResourceLocation;


public class CreateCompat {


    public static void setup(){
        AllInteractionBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.BASIC_SPELL_TURRET.get() ? new BasicTurretBehavior() : null);
        AllMovementBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.BASIC_SPELL_TURRET.get() ? new AbstractTurretBehavior() : null);
        AllMovementBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.TIMER_SPELL_TURRET.get() ? new TimerTurretBehavior() : null);
        AllMovementBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.ENCHANTED_SPELL_TURRET.get() ? new EnhancedTurretBehavior() : null);
        AllMovementBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.SOURCE_JAR.get() ? new SourceJarBehavior() : null);
        AllMovementBehaviours.registerBehaviourProvider(state -> state.getBlock() == BlockRegistry.CREATIVE_SOURCE_JAR.get() ? new SourceJarBehavior() : null);
        BlockStressDefaults.DEFAULT_CAPACITIES.put(new ResourceLocation(ArsCreo.MODID, "starbuncle_wheel"), 16.0);
        AllDisplayBehaviours.assignBlockEntity(AllDisplayBehaviours.register(new ResourceLocation(ArsCreo.MODID,"turret_target"),new TurretDisplaySource()),new ResourceLocation("ars_nouveau", LibBlockNames.BASIC_SPELL_TURRET));
        AllDisplayBehaviours.assignBlockEntity(AllDisplayBehaviours.register(new ResourceLocation(ArsCreo.MODID,"turret_target"),new TurretDisplaySource()),new ResourceLocation("ars_nouveau",LibBlockNames.ENCHANTED_SPELL_TURRET));
        AllDisplayBehaviours.assignBlockEntity(AllDisplayBehaviours.register(new ResourceLocation(ArsCreo.MODID,"turret_target"),new TurretDisplaySource()),new ResourceLocation("ars_nouveau",LibBlockNames.ROTATING_SPELL_TURRET));
        AllDisplayBehaviours.assignBlockEntity(AllDisplayBehaviours.register(new ResourceLocation(ArsCreo.MODID,"turret_target"),new TurretDisplaySource()),new ResourceLocation("ars_nouveau",LibBlockNames.TIMER_SPELL_TURRET));

    }

}
