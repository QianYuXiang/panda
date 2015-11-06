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

public class Selector {
	private String   _text;
	private String   _textContains;
	private String   _textMatches;
	private String   _textStartsWith;
	private String   _className;
	private String   _classNameMatches;
	private String   _description;
	private String   _descriptionContains;
	private String   _descriptionMatches;
	private String   _descriptionStartsWith;
	private boolean  _checkable;
	private boolean  _checked;
	private boolean  _clickable;
	private boolean  _longClickable;
	private boolean  _scrollable;
	private boolean  _enabled;
	private boolean  _focusable;
	private boolean  _focused;
	private boolean  _selected;
	private String   _packageName;
	private String   _packageNameMatches;
	private String   _resourceId;
	private String   _resourceIdMatches;
	private int      _index;
	private int      _instance;
	private Selector[] _childOrSiblingSelector = new Selector[]{};
	private String[] _childOrSibling = new String[]{};

	private long     _mask = 0;

	public static final long MASK_TEXT = 0x01;
	public static final long MASK_TEXTCONTAINS = 0x02;
	public static final long MASK_TEXTMATCHES = 0x04;
	public static final long MASK_TEXTSTARTSWITH = 0x08;
	public static final long MASK_CLASSNAME = 0x10;
	public static final long MASK_CLASSNAMEMATCHES = 0x20;
	public static final long MASK_DESCRIPTION = 0x40;
	public static final long MASK_DESCRIPTIONCONTAINS = 0x80;
	public static final long MASK_DESCRIPTIONMATCHES = 0x0100;
	public static final long MASK_DESCRIPTIONSTARTSWITH = 0x0200;
	public static final long MASK_CHECKABLE = 0x0400;
	public static final long MASK_CHECKED = 0x0800;
	public static final long MASK_CLICKABLE = 0x1000;
	public static final long MASK_LONGCLICKABLE = 0x2000;
	public static final long MASK_SCROLLABLE = 0x4000;
	public static final long MASK_ENABLED = 0x8000;
	public static final long MASK_FOCUSABLE = 0x010000;
	public static final long MASK_FOCUSED = 0x020000;
	public static final long MASK_SELECTED = 0x040000;
	public static final long MASK_PACKAGENAME = 0x080000;
	public static final long MASK_PACKAGENAMEMATCHES = 0x100000;
	public static final long MASK_RESOURCEID = 0x200000;
	public static final long MASK_RESOURCEIDMATCHES = 0x400000;
	public static final long MASK_INDEX = 0x800000;
	public static final long MASK_INSTANCE = 0x01000000;

	public String getText() {
		return _text;
	}
	
	public void setText(String text) {
		this._text = text;
		this._mask |= Selector.MASK_TEXT;
	}
	
	public String getClassName() {
		return _className;
	}
	
	public void setClassName(String className) {
		this._className = className;
		this._mask |= Selector.MASK_CLASSNAME;
	}
	
	public String getDescription() {
		return _description;
	}
	
	public void setDescription(String description) {
		this._description = description;
		this._mask |= Selector.MASK_DESCRIPTION;
	}

	public String getTextContains() {
		return _textContains;
	}

	public void setTextContains(String _textContains) {
		this._textContains = _textContains;
		this._mask |= Selector.MASK_TEXTCONTAINS;
	}

	public String getTextMatches() {
		return _textMatches;
	}

	public void setTextMatches(String _textMatches) {
		this._textMatches = _textMatches;
		this._mask |= Selector.MASK_TEXTMATCHES;
	}

	public String getTextStartsWith() {
		return _textStartsWith;
	}

	public void setTextStartsWith(String _textStartsWith) {
		this._textStartsWith = _textStartsWith;
		this._mask |= Selector.MASK_TEXTSTARTSWITH;
	}

	public String getClassNameMatches() {
		return _classNameMatches;
	}

	public void setClassNameMatches(String _classNameMatches) {
		this._classNameMatches = _classNameMatches;
		this._mask |= Selector.MASK_CLASSNAMEMATCHES;
	}

	public String getDescriptionContains() {
		return _descriptionContains;
	}

	public void setDescriptionContains(String _descriptionContains) {
		this._descriptionContains = _descriptionContains;
		this._mask |= Selector.MASK_DESCRIPTIONCONTAINS;
	}

	public String getDescriptionMatches() {
		return _descriptionMatches;
	}

	public void setDescriptionMatches(String _descriptionMatches) {
		this._descriptionMatches = _descriptionMatches;
		this._mask |= Selector.MASK_DESCRIPTIONMATCHES;
	}

	public String getDescriptionStartsWith() {
		return _descriptionStartsWith;
	}

	public void setDescriptionStartsWith(String _descriptionStartsWith) {
		this._descriptionStartsWith = _descriptionStartsWith;
		this._mask |= Selector.MASK_DESCRIPTIONSTARTSWITH;
	}

	public boolean isCheckable() {
		return _checkable;
	}

	public void setCheckable(boolean _checkable) {
		this._checkable = _checkable;
		this._mask |= Selector.MASK_CHECKABLE;
	}

	public boolean isChecked() {
		return _checked;
	}

	public void setChecked(boolean _checked) {
		this._checked = _checked;
		this._mask |= Selector.MASK_CHECKED;
	}

	public boolean isClickable() {
		return _clickable;
	}

	public void setClickable(boolean _clickable) {
		this._clickable = _clickable;
		this._mask |= Selector.MASK_CLICKABLE;
	}

	public boolean isScrollable() {
		return _scrollable;
	}

	public void setScrollable(boolean _scrollable) {
		this._scrollable = _scrollable;
		this._mask |= Selector.MASK_SCROLLABLE;
	}

	public boolean isLongClickable() {
		return _longClickable;
	}

	public void setLongClickable(boolean _longClickable) {
		this._longClickable = _longClickable;
		this._mask |= Selector.MASK_LONGCLICKABLE;
	}

	public boolean isEnabled() {
		return _enabled;
	}

	public void setEnabled(boolean _enabled) {
		this._enabled = _enabled;
		this._mask |= Selector.MASK_ENABLED;
	}

	public boolean isFocusable() {
		return _focusable;
	}

	public void setFocusable(boolean _focusable) {
		this._focusable = _focusable;
		this._mask |= Selector.MASK_FOCUSABLE;
	}

	public boolean isFocused() {
		return _focused;
	}

	public void setFocused(boolean _focused) {
		this._focused = _focused;
		this._mask |= Selector.MASK_FOCUSED;
	}

	public boolean isSelected() {
		return _selected;
	}

	public void setSelected(boolean _selected) {
		this._selected = _selected;
		this._mask |= Selector.MASK_SELECTED;
	}

	public String getPackageName() {
		return _packageName;
	}

	public void setPackageName(String _packageName) {
		this._packageName = _packageName;
		this._mask |= Selector.MASK_PACKAGENAME;
	}

	public String getPackageNameMatches() {
		return _packageNameMatches;
	}

	public void setPackageNameMatches(String _packageNameMatches) {
		this._packageNameMatches = _packageNameMatches;
		this._mask |= Selector.MASK_PACKAGENAMEMATCHES;
	}

	public String getResourceId() {
		return _resourceId;
	}

	public void setResourceId(String _resourceId) {
		this._resourceId = _resourceId;
		this._mask |= Selector.MASK_RESOURCEID;
	}

	public String getResourceIdMatches() {
		return _resourceIdMatches;
	}

	public void setResourceIdMatches(String _resourceIdMatches) {
		this._resourceIdMatches = _resourceIdMatches;
		this._mask |= Selector.MASK_RESOURCEIDMATCHES;
	}

	public int getIndex() {
		return _index;
	}

	public void setIndex(int _index) {
		this._index = _index;
		this._mask |= Selector.MASK_INDEX;
	}

	public int getInstance() {
		return _instance;
	}

	public void setInstance(int _instance) {
		this._instance = _instance;
		this._mask |= Selector.MASK_INSTANCE;
	}

	public long getMask() {
		return _mask;
	}

	public void setMask(long _mask) {
		this._mask = _mask;
	}

    public Selector[] getChildOrSiblingSelector() {
        return _childOrSiblingSelector;
    }

    public void setChildOrSiblingSelector(Selector[] _childOrSiblingSelector) {
        this._childOrSiblingSelector = _childOrSiblingSelector;
    }

    public String[] getChildOrSibling() {
        return _childOrSibling;
    }

    public void setChildOrSibling(String[] _childOrSibling) {
        this._childOrSibling = _childOrSibling;
    }
}
