package com.comino.msp.execution.autopilot.offboard;

import com.comino.msp.model.DataModel;

public interface IOffboardTargetAction {


	public void action(DataModel model, float delta);

}