package com.qxf.easysql;

import com.qxf.library.db.EasyTable;

import java.util.Arrays;

public class Table1 extends EasyTable {

    private byte _byte;
    private long _long;
    private float _float;
    private short _short;
    private byte[] bytes;
    private double _double;
    private String _string;
    private boolean _bit;
    private int _int;

    public Table1() {
    }

    public Table1(byte _byte, long _long, float _float, short _short, byte[] bytes, double _double, String _string, boolean _bit, int _int) {
        this._byte = _byte;
        this._long = _long;
        this._float = _float;
        this._short = _short;
        this.bytes = bytes;
        this._double = _double;
        this._string = _string;
        this._bit = _bit;
        this._int = _int;
    }

    public byte get_byte() {
        return _byte;
    }

    public void set_byte(byte _byte) {
        this._byte = _byte;
    }

    public long get_long() {
        return _long;
    }

    public void set_long(long _long) {
        this._long = _long;
    }

    public float get_float() {
        return _float;
    }

    public void set_float(float _float) {
        this._float = _float;
    }

    public short get_short() {
        return _short;
    }

    public void set_short(short _short) {
        this._short = _short;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public double get_double() {
        return _double;
    }

    public void set_double(double _double) {
        this._double = _double;
    }

    public String get_string() {
        return _string;
    }

    public void set_string(String _string) {
        this._string = _string;
    }

    public boolean get_bit() {
        return _bit;
    }

    public void set_bit(boolean _bit) {
        this._bit = _bit;
    }

    public int get_int() {
        return _int;
    }

    public void set_int(int _int) {
        this._int = _int;
    }

    @Override
    public String toString() {
        return "Table1{" +
                "_byte=" + _byte +
                ", _long=" + _long +
                ", _float=" + _float +
                ", _short=" + _short +
                ", bytes=" + Arrays.toString(bytes) +
                ", _double=" + _double +
                ", _string='" + _string + '\'' +
                ", _bit=" + _bit +
                ", _int=" + _int +
                '}';
    }
}
