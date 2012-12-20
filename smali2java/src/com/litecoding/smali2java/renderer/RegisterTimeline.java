package com.litecoding.smali2java.renderer;

import java.util.ArrayList;
import java.util.List;

import com.litecoding.smali2java.entity.smali.Param;
import com.litecoding.smali2java.entity.smali.RegisterInfo;

public class RegisterTimeline {
	private boolean isInitialized = false;
	private int localRegisters = 0;
	private int params = 0;
	private boolean isMethodStatic = false;
	
	private int sliceLength = 0;
	private int linesCount = 0;
	
	private ArrayList<ArrayList<RegisterInfo>> timeline = null;
	
	public RegisterTimeline() {
		this.isInitialized = false;
	}
	
	public RegisterTimeline(int localRegisters, List<Param> params, 
			boolean isMethodStatic, int linesCount) {
		this.isInitialized = true;
		init(localRegisters, params, isMethodStatic, linesCount);
	}
	
	public void init(int localRegisters, List<Param> params, 
			boolean isMethodStatic, int linesCount) {
		if(isInitialized)
			return;
		
		//TODO: refactor this due to parameter mapping ability in SmaliMethod
		this.localRegisters = localRegisters;
		this.params = 0;
		this.isMethodStatic = isMethodStatic;
		this.linesCount = linesCount;
		
		for(Param param : params) {
			if("J".equals(param.getType()) ||
					"D".equals(param.getType())) {
				this.params += 2;
			} else {
				this.params++;
			}
		}
		
		int len = this.localRegisters + this.params;
		if(!this.isMethodStatic)
			len++;
		
		this.sliceLength = len;
		
		this.timeline = new ArrayList<ArrayList<RegisterInfo>>(linesCount);
		for(int i = 0; i < linesCount; i++) {
			ArrayList<RegisterInfo> slice = new ArrayList<RegisterInfo>(len);
			this.timeline.add(slice);
			for(int j = 0; j < len; j++)
				slice.add(new RegisterInfo());
		}
	}
	
	public boolean isInitialized() {
		return isInitialized;
	}
	
	public int getSliceLength() {
		return sliceLength;
	}
	
	public int getLinesCount() {
		return linesCount;
	}
	
	public List<? extends List<RegisterInfo>> getTimeline() {
		return timeline;
	}
	
	public List<RegisterInfo> getSlice(int line) {
		return timeline.get(line);
	}
	
	public RegisterInfo getRegister(int line, int register) {
		return timeline.get(line).get(register);
	}
	
}