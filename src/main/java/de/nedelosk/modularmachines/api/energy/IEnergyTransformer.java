package de.nedelosk.modularmachines.api.energy;

import javax.annotation.Nonnull;

public interface IEnergyTransformer {

	long transform(long energy);

	@Nonnull
	IEnergyType getInputType();

	@Nonnull
	IEnergyType getOutputType();
}
