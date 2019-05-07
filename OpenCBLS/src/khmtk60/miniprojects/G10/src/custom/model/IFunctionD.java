package custom.model;

import localsearch.model.Invariant;
import localsearch.model.VarIntLS;

public interface IFunctionD extends Invariant {
    public double getMinValue();
    public double getMaxValue();
    public double getValue();
    public double getAssignDelta(VarIntLS x, int val);
    public double getSwapDelta(VarIntLS x, VarIntLS y);
}
