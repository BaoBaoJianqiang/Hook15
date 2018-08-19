package jianqiang.com.hook1;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * @author weishu
 * @date 16/1/28
 */
public class EvilInstrumentation extends Instrumentation {

    private static final String TAG = "EvilInstrumentation";

    // ActivityThread中原始的对象, 保存起来
    Instrumentation mBase;

    public EvilInstrumentation(Instrumentation base) {
        mBase = base;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {

        Log.d(TAG, "XXX到此一游!");

        // 开始调用原始的方法, 调不调用随你,但是不调用的话, 所有的startActivity都失效了.
        // 由于这个方法是隐藏的,因此需要使用反射调用;首先找到这个方法
        Class[] p1 = {Context.class, IBinder.class,
                IBinder.class, Activity.class,
                Intent.class, int.class, Bundle.class};
        Object[] v1 = {who, contextThread, token, target,
                intent, requestCode, options};

        return (ActivityResult) RefInvoke.invokeInstanceMethod(
                mBase, "execStartActivity", p1, v1);
    }

    public Activity newActivity(ClassLoader cl, String className,
                                Intent intent)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException {

        return mBase.newActivity(cl, className, intent);
    }
}

