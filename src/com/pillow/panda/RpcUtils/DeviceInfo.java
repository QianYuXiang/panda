/*
 * The MIT License (MIT)
 * Copyright (c) 2015 xiaocong@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.pillow.panda.RpcUtils;

public class DeviceInfo {
	private String currentPackageName;
	private int displayWidth;
	private int displayHeight;
	private int displayRotation;
	private int displaySizeDpX;
	private int displaySizeDpY;
	private String productName;
	private boolean naturalOrientation;
    private boolean screenOn;
	
	private int sdkInt;

	public String getCurrentPackageName() {
		return currentPackageName;
	}

	public void setCurrentPackageName(String currentPackageName) {
		this.currentPackageName = currentPackageName;
	}

	public int getDisplayWidth() {
		return displayWidth;
	}

	public void setDisplayWidth(int displayWidth) {
		this.displayWidth = displayWidth;
	}

	public int getDisplayHeight() {
		return displayHeight;
	}

	public void setDisplayHeight(int displayHeight) {
		this.displayHeight = displayHeight;
	}

	public int getDisplayRotation() {
		return displayRotation;
	}

	public void setDisplayRotation(int displayRotation) {
		this.displayRotation = displayRotation;
	}

	public int getDisplaySizeDpX() {
		return displaySizeDpX;
	}

	public void setDisplaySizeDpX(int displaySizeDpX) {
		this.displaySizeDpX = displaySizeDpX;
	}

	public int getDisplaySizeDpY() {
		return displaySizeDpY;
	}

	public void setDisplaySizeDpY(int displaySizeDpY) {
		this.displaySizeDpY = displaySizeDpY;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public boolean isNaturalOrientation() {
		return naturalOrientation;
	}

	public void setNaturalOrientation(boolean naturalOrientation) {
		this.naturalOrientation = naturalOrientation;
	}

	public boolean isScreenOn() {
		return screenOn;
	}

	public void setScreenOn(boolean screenOn) {
		this.screenOn = screenOn;
	}

	public int getSdkInt() {
		return sdkInt;
	}

	public void setSdkInt(int sdkInt) {
		this.sdkInt = sdkInt;
	}
}
