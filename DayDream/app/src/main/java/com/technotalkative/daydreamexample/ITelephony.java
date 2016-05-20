// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.technotalkative.daydreamexample;

import android.os.IInterface;

public interface ITelephony
    extends IInterface
{

    public abstract void answerRingingCall();

    public abstract void dial(String s);

    public abstract boolean endCall();
}
