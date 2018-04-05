package com.lyso.lab.lab3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.View;

/**
 * We use the canvas to draw the on-board shape.
 * Ole Lysø 3.5.2018
 */
public class InterfaceView extends View {

    private RectF            borderPosition;
    private final CircleBall circleBall   = new CircleBall();
    private final Paint      drawPen      = new Paint();
    private long             lastDrawTime = 0;
    private float[]          sensorValues = new float[3];

    /**
     * We use SetFocusable to make the circle visible for us. Meaning r=5 percent of screen width.
     *
     */
    public InterfaceView(final Context context) {
        super(context);
        setFocusable(true);

        this.initCircleBall();
    }

    /**
     * Draws the border and circle on the canvas.
     *
     */
    protected void onDraw(final Canvas canvas) {
        long  currentTime = System.currentTimeMillis();
        float deltaTime   = (1.0f / (float)(currentTime - this.lastDrawTime));

        this.lastDrawTime = currentTime;

        if (this.borderPosition == null)
            this.initPosition(canvas);

        this.circleBall.move(deltaTime, this.borderPosition, this.sensorValues);

        canvas.drawColor(Color.WHITE);
        this.drawBorder(canvas);
        this.drawCircle(canvas);
    }

    /**
     * Draws the board.
     */
    private void drawBorder(final Canvas canvas) {
        this.drawPen.setAntiAlias(true);
        this.drawPen.setColor(Color.WHITE);
        this.drawPen.setStyle(Paint.Style.FILL);
        //this.drawPen.setStrokeWidth(Constants.RECT_BORDER);

        canvas.drawRect(
            Constants.RECT_MARGIN, Constants.RECT_MARGIN,
            (canvas.getWidth() - Constants.RECT_MARGIN), (canvas.getHeight() - Constants.RECT_MARGIN),
            this.drawPen
        );
    }

    /**
     * Drawing the circle
     */
    private void drawCircle(final Canvas canvas) {
        PointF position = this.circleBall.getPosition();

        this.drawPen.setColor(Color.BLACK);
        this.drawPen.setStyle(Paint.Style.FILL);

        canvas.drawCircle(position.x, position.y, this.circleBall.getRadius(), this.drawPen);
    }

    /**
     * Sets up the initial properties of the circle ball.
     */
    private void initCircleBall() {
        Point displaySize = new Point();
        ((Activity)this.getContext()).getWindowManager().getDefaultDisplay().getSize(displaySize);

        this.circleBall.setRadius((float)displaySize.x * Constants.BALL_SIZE_PERCENT);
        this.circleBall.setSpeed(Constants.BALL_INIT_SPEED);
    }

    /**
     * Calculates the initial starting-positions for 'everything'.
     * This is because of varying device sizes so the interface is relative.
     */
    private void initPosition(final Canvas canvas) {
        this.borderPosition = new RectF(
            Constants.RECT_MARGIN, Constants.RECT_MARGIN,
            (canvas.getWidth() - Constants.RECT_MARGIN), (canvas.getHeight() - Constants.RECT_MARGIN)
        );

        this.circleBall.setPosition(
            new PointF(this.borderPosition.centerX(), this.borderPosition.centerY())
        );
    }

    /**
     * Updates the values for the accelerator sensor.
     *
     */
    public void setSensorValues(final float[] sensorValues) {
        this.sensorValues = sensorValues;
    }

}
