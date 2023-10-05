package lach_01298.qmd.multiblock.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.IAcceleratorPart;
import lach_01298.qmd.accelerator.tile.TileMassSpectrometerController;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import nc.network.multiblock.MultiblockUpdatePacket;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.heat.HeatBuffer;
import net.minecraft.util.math.BlockPos;

public class MassSpectrometerUpdatePacket extends AcceleratorUpdatePacket
{
	public double workDone, recipeWork;
	
	public MassSpectrometerUpdatePacket() {
		super();
	}
	
	public MassSpectrometerUpdatePacket(BlockPos pos, boolean isAcceleratorOn, long cooling, long rawHeating, long currentHeating, int maxCoolantIn, int maxCoolantOut,
			int maxOperatingTemp, int requiredEnergy, double efficiency, int acceleratingVoltage, int RFCavityNumber, int quadrupoleNumber,
			double quadrupoleStrength, int dipoleNumber, double dipoleStrength, int errorCode ,HeatBuffer heatBuffer, EnergyStorage energyStorage, List<Tank> tanks,List<ParticleStorageAccelerator> beams,
			double workDone, double recipeWork)
	{
		super(pos, isAcceleratorOn, cooling, rawHeating, currentHeating, maxCoolantIn, maxCoolantOut, maxOperatingTemp, requiredEnergy, efficiency, acceleratingVoltage,
				RFCavityNumber, quadrupoleNumber, quadrupoleStrength, dipoleNumber, dipoleStrength, errorCode, heatBuffer, energyStorage,tanks, beams);
		this.workDone = workDone;
		this.recipeWork = recipeWork;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);
		
		workDone = buf.readDouble();
		recipeWork = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);
		buf.writeDouble(workDone);
		buf.writeDouble(recipeWork);
	}
	
	public static class Handler extends MultiblockUpdatePacket.Handler<Accelerator, IAcceleratorPart, AcceleratorUpdatePacket, TileMassSpectrometerController, MassSpectrometerUpdatePacket> {
		
		public Handler() {
			super(TileMassSpectrometerController.class);
		}
		
		@Override
		protected void onPacket(MassSpectrometerUpdatePacket message, Accelerator multiblock) {
			multiblock.onMultiblockUpdatePacket(message);
		}
	}
}
