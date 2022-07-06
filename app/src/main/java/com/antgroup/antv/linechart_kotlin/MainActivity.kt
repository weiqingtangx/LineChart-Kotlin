package com.antgroup.antv.linechart_kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.antgroup.antv.f2.*
import com.antgroup.antv.f2.F2Chart.*

class MainActivity : AppCompatActivity(), F2CanvasView.Adapter {
    private var mCanvasView: F2CanvasView? = null
    private var mChart: F2Chart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mCanvasView = findViewById(R.id.canvasView) as? F2CanvasView;
        mCanvasView!!.initCanvasContext();
        mCanvasView!!.setAdapter(this);
    }

    override fun onCanvasDraw(canvasView: F2CanvasView) {
        if (mChart == null) {
            mChart = create(
                canvasView.context,
                "LineChart-Kotlin",
                canvasView.width.toDouble(),
                canvasView.height.toDouble()
            )
        }
        mChart!!.setCanvas(canvasView)
        mChart!!.padding(10.0, 10.0, 10.0, 10.0)
        val source: String =
            Utils.loadAssetFile(canvasView.context, "data.json")
        mChart!!.source(source)
        mChart!!.line().position("genre*sold")
        mChart!!.setScale("genre", ScaleConfigBuilder().tickCount(5))
        mChart!!.render()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mChart != null) {
            mChart!!.destroy()
        }
    }
}