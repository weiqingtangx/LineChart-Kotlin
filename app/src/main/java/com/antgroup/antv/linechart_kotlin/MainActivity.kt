package com.antgroup.antv.linechart_kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.antgroup.antv.f2.*
import com.antgroup.antv.f2.F2Chart.*

//需要继承F2CanvasView.Adapter在，其回调函数中绘制和销毁对象
class MainActivity : AppCompatActivity(), F2CanvasView.Adapter {
    //声明对象
    //F2CanvasView是展示折线图的View
    private var mCanvasView: F2CanvasView? = null
    //F2Chart 是负责图表绘制
    private var mChart: F2Chart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //关联xml中的view
        mCanvasView = findViewById(R.id.canvasView) as? F2CanvasView;
        //初始化view
        mCanvasView!!.initCanvasContext();
        //设置adapter
        mCanvasView!!.setAdapter(this);
    }

    //adapter的绘制回调，图表绘制操作在这里
    override fun onCanvasDraw(canvasView: F2CanvasView) {
        //初始化chart， 因为需要canvasView的宽和高，在这里初始化确保能拿到canvasView的宽和高
        if (mChart == null) {
            mChart = create(
                canvasView.context,
                "LineChart-Kotlin",
                canvasView.width.toDouble(),
                canvasView.height.toDouble()
            )
        }
        //关联chart和canvasView，chart最后需要把图表显示在canvasView上
        mChart!!.setCanvas(canvasView)
        //设置chart的内边距
        mChart!!.padding(10.0, 10.0, 10.0, 10.0)
        //使用utils加载数据
        val source: String =
            Utils.loadAssetFile(canvasView.context, "data.json")
        //把数据设置到chart中
        mChart!!.source(source)
        //在chart上画一条折线，折线的x轴和y轴的数据映射分别是genre， sold字段
        mChart!!.line().position("genre*sold")
        //渲染 并显示在canvasView上
        mChart!!.render()
    }

    //adapter的销毁回调，用来销毁chart
    override fun onDestroy() {
        super.onDestroy()
        if (mChart != null) {
            mChart!!.destroy()
        }
    }
}