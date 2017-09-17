package com.comino.msp.execution.flightcontrol;

import org.mavlink.messages.MAV_SEVERITY;

import com.comino.msp.execution.offboard.OffboardPositionUpdater;
import com.comino.msp.log.MSPLogger;
import com.comino.msp.model.DataModel;
import com.comino.msp.utils.MSPConvertUtils;

import georegression.struct.point.Vector3D_F32;
import georegression.struct.se.Se3_F32;

public class FlightGestureControl {

	private OffboardPositionUpdater offboard = null;
	private DataModel               model    = null;
	private MSPLogger               logger   = null;

	// Circle mode
	private Se3_F32 circleCenter = new Se3_F32();
	private Se3_F32 circleTarget = new Se3_F32();
	private float inc;
	private Vector3D_F32 circleDelta = new Vector3D_F32();


	public FlightGestureControl(DataModel model, OffboardPositionUpdater offboard) {
		this.offboard = offboard;
		this.model    = model;
		this.logger   = MSPLogger.getInstance();
	}

	public void setTargetAndExecute(float x, float y, float z, float yaw) {
		Se3_F32 target = new Se3_F32();
		MSPConvertUtils.convertToSe3_F32(x, y, z, 0, 0, yaw, target);
		offboard.setNextTarget(target);
	}

	public void jumpback(int distance) {
		Se3_F32 target = new Se3_F32();
		MSPConvertUtils.convertModelXYToSe3_F32(model, target);
		// TODO: Add math here to execute jumpback
		offboard.setNextTarget(target);
	}

	public void enableCircleMode(boolean enable) {
		if(enable) {
			inc = 0;
			MSPConvertUtils.convertModelXYToSe3_F32(model, circleCenter);
            circleTarget.set(circleCenter);
            circleDelta.set((float)Math.sin(inc), (float)Math.cos(inc), 0);
            circleTarget.T.plusIP(circleDelta);

			offboard.addListener((Se3_F32 p,int t) -> {
				inc = inc+0.1f;
			    circleTarget.set(circleCenter);
	            circleDelta.set((float)Math.sin(inc), (float)Math.cos(inc), (float)Math.sin(inc)/3f);
	            circleTarget.T.plusIP(circleDelta);
	            offboard.setNextTarget(circleTarget, OffboardPositionUpdater.MODE_MULTI_TARGET);
			});
			offboard.setNextTarget(circleTarget, OffboardPositionUpdater.MODE_MULTI_TARGET);
			logger.writeLocalMsg("[msp] Circlemode activated",MAV_SEVERITY.MAV_SEVERITY_INFO);
		}
		else {
			offboard.removeListeners();
			offboard.addListener((Se3_F32 p,int t) -> {
				logger.writeLocalMsg("[msp] Circlemode stopped",MAV_SEVERITY.MAV_SEVERITY_INFO);
			});
			offboard.setNextTarget(circleCenter);
		}
	}

}
