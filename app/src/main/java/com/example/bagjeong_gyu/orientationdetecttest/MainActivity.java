package com.example.bagjeong_gyu.orientationdetecttest;

import android.content.res.Configuration;
import android.graphics.Interpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int mPreviousOrientation=-2;
    private int mCurrentOrientation;
    private int mPreviousOrientationForRotation;
    private int mPreviousEndDegree;
    private boolean mIsFirstRotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIsFirstRotation = true;
        //initSimpleOrientationListener();
        initOrientationListener();
    }

    private void initSimpleOrientationListener() {
        SimpleOrientationListener mOrientationListener = new SimpleOrientationListener(
                this) {

            @Override
            public void onSimpleOrientationChanged(int orientation) {
                if(orientation == Configuration.ORIENTATION_LANDSCAPE){

                    Toast.makeText(MainActivity.this, "ORIENTATION_LANDSCAPE", Toast.LENGTH_SHORT).show();
                }else if(orientation == Configuration.ORIENTATION_PORTRAIT){
                    Toast.makeText(MainActivity.this, "ORIENTATION_PORTRAIT", Toast.LENGTH_SHORT).show();
                }
            }
        };
        mOrientationListener.enable();
    }

    private void initOrientationListener() {

        OrientationEventListener mOrientationListener = new OrientationEventListener(
                this) {


            @Override
            public void onOrientationChanged(int orientation) {


                int currentOrientation = OrientationEventListener.ORIENTATION_UNKNOWN;

                if (orientation >= 330 || orientation < 30) {
                    currentOrientation = 0;
                } else if (orientation >= 60 && orientation < 120) {
                    currentOrientation = 90;
                } else if (orientation >= 150 && orientation < 210) {
                    currentOrientation = 180;
                } else if (orientation >= 240 && orientation < 300) {
                    currentOrientation = 270;
                }

                if(currentOrientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                    return;
                }

                Log.d("rotate", "orientation: " + orientation + " current orientation: " + currentOrientation + " previous orientation: " + mPreviousOrientation);


                 if(currentOrientation != mPreviousOrientation) {
                    mCurrentOrientation = currentOrientation;

                     rotateButton();
                }

                mPreviousOrientation = currentOrientation;
            }
        };
        mOrientationListener.enable();
    }

    private void rotateButton() {
//        if(mIsFirstRotation || mCurrentOrientation < 0) {
//            mIsFirstRotation = false;
//            return;
//        }
        //Log.d("rotate", "current orientation: " + mCurrentOrientation + " previous rotation degree: " + mPreviousOrientationForRotation);
        ImageButton captureButton = (ImageButton) findViewById(R.id.capture);


        // Create an animation instance
        int startDegree = 0;
        int endDegree = 0;

        startDegree = mPreviousOrientationForRotation;
        //시계반대방향
        if(mCurrentOrientation == 270 && mPreviousOrientationForRotation == 0) {
            startDegree = 0;
            endDegree = 90;
            //시계반대방향
        } else if (mCurrentOrientation == 0 && mPreviousOrientationForRotation == 270) {
            startDegree = 90;
            endDegree = 0;
        } else if (mCurrentOrientation == 270 && mPreviousOrientationForRotation == 180) {
            startDegree = 180;
            endDegree = 90;
        } else if (mCurrentOrientation == 180 && mPreviousOrientationForRotation == 270) {
            startDegree = 90;
            endDegree = 180;
        }else if (mCurrentOrientation == 180 && mPreviousOrientationForRotation == 90) {
            startDegree = 270;
            endDegree = 180;
        }else if (mCurrentOrientation == 90 && mPreviousOrientationForRotation == 180) {
            startDegree = 180;
            endDegree = 270;
        }
        else if (mCurrentOrientation == 90 && mPreviousOrientationForRotation == 0) {
            startDegree = 360;
            endDegree = 270;
        }else if (mCurrentOrientation == 0 && mPreviousOrientationForRotation == 90) {
            startDegree = 270;
            endDegree = 360;
        }

//        startDegree = mPreviousOrientationForRotation;
//        //시계반대방향
//        if(mCurrentOrientation == 270 && mPreviousOrientationForRotation == 0) {
//            startDegree = 0;
//            endDegree = 90;
//            //시계반대방향
//        } else if (mCurrentOrientation == 0 && mPreviousOrientationForRotation == 270) {
//            startDegree = 90;
//            endDegree = 0;
//        } else if (mCurrentOrientation > mPreviousOrientationForRotation) {
//            startDegree = mPreviousEndDegree;
//            endDegree = (mPreviousEndDegree - 90);
//        } else {
//            startDegree = mPreviousEndDegree;
//            endDegree = (mPreviousEndDegree + 90);
//        }


//        if(mCurrentOrientation > mPreviousOrientationForRotation) {
//            endDegree = startDegree + 90;
//        } else {
//            endDegree = startDegree - 90;
//        }


//        if(mPreviousOrientationForRotation > (360 - mCurrentOrientation)) {
//            startDegree = (360 - mCurrentOrientation);
//            endDegree = mPreviousOrientationForRotation;
//        } else  {
//            startDegree = mPreviousOrientationForRotation;
//            endDegree = (360 - mCurrentOrientation);
//        }

        Log.d("rotate", "start degree: " + startDegree + " end degree: " + endDegree);
        RotateAnimation animation = new RotateAnimation(startDegree,endDegree,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        // Set the animation's parameters

        animation.setDuration(1000);               // duration in ms

//        animation.setRepeatCount(0);                // -1 = infinite repeated
//        animation.setRepeatMode(Animation.REVERSE); // reverses each repeat
        animation.setFillAfter(true);
        captureButton.startAnimation(animation);
        mPreviousOrientationForRotation = mCurrentOrientation;
        mPreviousEndDegree = endDegree;
    }
}
