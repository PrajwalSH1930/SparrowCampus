package com.example.sparrowcampus;

import android.content.Context;
import android.view.MotionEvent;

import org.rajawali3d.Object3D;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.loader.LoaderOBJ;
import org.rajawali3d.loader.ParsingException;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.renderer.Renderer;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

public class MapRenderer extends Renderer {

    private Object3D objModel;
    private Context mContext;

    public MapRenderer(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void initScene() {
        // Set up lighting
        DirectionalLight light = new DirectionalLight(1.0, 1.0, -1.0);
        //light.setColor(1.0f, 1.0f, 1.0f);
        light.setPower(2.0f);
        getCurrentScene().addLight(light);

        // Set up the camera
        getCurrentCamera().setPosition(0, 1, 6);

        // Load the OBJ model
        try {
            // Replace R.raw.campusfinalobj with your actual .obj file
            LoaderOBJ objParser = new LoaderOBJ(this, R.raw.pqr);
            objParser.parse();
            objModel = objParser.getParsedObject();
            getCurrentScene().addChild(objModel);
            //loadMtlFile(R.raw.ssfile, objModel);
        } catch (ParsingException e) {
            e.printStackTrace();
        }
    }


    /*@Override
    protected void initScene() {
        // Set up lighting
        DirectionalLight light = new DirectionalLight(1.0, 1.0, -1.0);
        light.setColor(1.0f, 1.0f, 1.0f);
        light.setPower(2.0f);
        getCurrentScene().addLight(light);

        DirectionalLight ambientLight = new DirectionalLight(0.0, -1.0, 0.0);
        ambientLight.setColor(0.2f, 0.2f, 0.2f);
        getCurrentScene().addLight(ambientLight);

        // Set up the camera
        getCurrentCamera().setPosition(0, 0, 10);
        getCurrentCamera().setLookAt(0, 0, 0);

        // Load the STL model
        try {
            InputStream inputStream = mContext.getResources().openRawResource(R.raw.ss2); // Ensure you have an STL file named 'last.stl' in res/raw
            objModel = loadSTLObject(inputStream);
            if (objModel != null) {
                getCurrentScene().addChild(objModel);
            } else {
                Log.e("MapRenderer", "Model is null. Check STL loading.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("MapRenderer", "Error loading STL model.");
        }
    }*/

    private Object3D loadSTLObject(InputStream inputStream) throws IOException {
        // Placeholder for STL loading logic
        // You'll need to replace this with actual STL parsing code using a library such as Assimp or your own implementation
        // For demonstration purposes, returning a dummy Object3D
        return new Object3D();
    }

    @Override
    public void onRenderFrame(GL10 glUnused) {
        // Rotate the model
        if (objModel != null) {
            objModel.rotate(Vector3.Axis.Y, 1.0);
        }
        super.onRenderFrame(glUnused);
    }

    @Override
    public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
        // Implement as needed
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        // Implement as needed
    }
}
