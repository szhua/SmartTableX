package com.fencer.lock


import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bin.david.form.core.SmartTable
import com.bin.david.form.data.CellInfo
import com.bin.david.form.data.CellRange
import com.bin.david.form.data.column.Column
import com.bin.david.form.data.format.bg.BaseBackgroundFormat
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat
import com.bin.david.form.data.style.FontStyle
import com.bin.david.form.data.style.LineStyle
import com.bin.david.form.data.table.TableData
import com.bin.david.form.utils.DensityUtils
import com.fencer.lock.format.FixedWidthFormat
import com.fencer.lock.format.RiverNameFormat


data class  Indexes(var startIndex:Int ,var endIndex :Int)


class MainActivity : AppCompatActivity() {


    private val viewModel by viewModels<ReportViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val smartTable = findViewById<SmartTable<AutoBean>>(R.id.smart_table)

        viewModel.data.observe(this){
            it?.let {
                setTable(it,smartTable)
            }
        }
        viewModel.loadData()




    }

    private fun setTable(datas : List<AutoBean>, smartTable: SmartTable<AutoBean>) {
        for (autoBean in datas) {
            autoBean.rvnm = autoBean.rvnm.split("").joinToString("\n")
        }

        val columns = mutableListOf(
            Column<String>("河道","rvnm").apply {
                isFixed=true
                drawFormat=
                    RiverNameFormat(this@MainActivity, 20)
            },
            Column<String>("闸坝名称","gcnm").apply {
                isFixed =true
               drawFormat = FixedWidthFormat(DensityUtils.dp2px(this@MainActivity,40f))
            },
            Column<Any>("闸孔尺寸",
                listOf( Column<String>("孔数","szks"),
                    Column<String>("孔宽(m)","szks"),
                    Column<String>("门高(m)","szks"))),
            Column<String>("闸底/河宽\n高程(m)","szks"),
            Column<String>("汛初\n(6-1～6-20)\n汛限水位(m)","szks"),
            Column<String>("汛末\n(9-19～9-30)\n汛限水位(m)","szks"),
            Column<Any>("设计正常水位",
                Column<String>("水位(m)","szks"),
                Column<String>("闸前水深(m)","szks"),),
            Column<Any>("六四年雨型排涝",
                Column<String>("水位(m)","szks"),
                Column<String>("闸前水深(m)","szks"),
                Column<String>("流量(m³/s)","szks")),
            Column<Any>("六一年雨型排涝",
                Column<String>("水位(m)","szks"),
                Column<String>("闸前水深(m)","szks"),
                Column<String>("流量(m³/s)","szks")),
            Column<Any>("水位(m)",
                Column<String>("闸上","szks"),
                Column<String>("闸下","szks"),
            ),
            Column<Any>("水深(m)",
                Column<String>("闸上","szks"),
                Column<String>("闸下","szks"),
            ),
            Column<String>("实际\n流量","szks"),
            Column<String>("开闸\n孔数","szks"),
            Column<String>("开启高\n度(m)","szks"),
            Column<String>("日降雨\n量(mm)","szks"),)

        val config = smartTable.config
        config.isShowXSequence=false
        config.isShowTableTitle=false
        config.isShowYSequence=false
        config.columnTitleBackground= BaseBackgroundFormat(Color.parseColor("#3976dc"))
        config.columnTitleStyle= FontStyle().apply {
            textColor =Color.parseColor("#ffffff")
            textSize=DensityUtils.sp2px(this@MainActivity,11f)
        }
        config.columnTitleVerticalPadding = DensityUtils.dp2px(this, 4f)
        config.contentStyle = FontStyle().apply {
            textColor =Color.parseColor("#ffffff")
            textSize=DensityUtils.sp2px(this@MainActivity,11f)
        }

        config.contentCellBackgroundFormat = object :BaseCellBackgroundFormat<CellInfo<*>>(){
            override fun getBackGroundColor(t: CellInfo<*>): Int {
                if ( datas[t.row].isSelected && t.col>=2){
                    return Color.parseColor("#c75450")
                }
                return  Color.parseColor("#ffffff")
            }

            override fun getTextColor(t: CellInfo<*>): Int {
                if ( datas[t.row].isSelected && t.col>=2){
                    return Color.parseColor("#ffffff")
                }
                return  Color.parseColor("#333333")
            }
        }

        smartTable.setZoom(true)
        val  tableData =  TableData("测试",datas,columns)
        tableData.userCellRange = arrayListOf()

        val rivers = mutableMapOf<String,Indexes>()
        datas.mapIndexed { index, row ->
            if (!rivers.contains(row.rvnm)){
                rivers[row.rvnm] = Indexes(index,index)
            }
            rivers[row.rvnm]?.endIndex = index
        }
        rivers.forEach { t ->
            val u =t.value
            tableData.userCellRange.add(CellRange(u.startIndex,u.endIndex,0,0))
        }

        tableData.setOnItemClickListener { column, value, t, col, row ->
            for (data in datas) {
                data.isSelected=false
            }
            val autoBean = datas[row]
            autoBean.isSelected=true
            smartTable.post {
                smartTable.reDraw()
            }
        }


        smartTable.tableData=tableData
    }
}