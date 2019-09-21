package foxapple.android.finacial.data.remote.tushare

import java.io.Serializable

class TuShareVO : Serializable {
    var api_name: String? = null //接口名称，比如stock_basic
    val token: String =
        "efdc8c2c7067b37db3819b073b24fbc256967a94db29966cf47c0555" //用户唯一标识，可通过登录pro网站获取
    var params: Map<String, String>? = null //接口参数，如daily接口中start_date和end_date
    var fields: String? = null //字段列表，用于接口获取指定的字段，以逗号分隔，如"open,high,low,close"
}

class TuShareResponseVO : Serializable {
    var code: String? = null //接口返回码，2002表示权限问题。
    var message: String? = null //错误信息，比如“系统内部错误”，“没有权限”等
    var data: TuShareDataVO? = null //数据，data里包含fields和items字段，分别为字段和数据内容

    override fun toString(): String {
        return "TuShareResponseVO(code=$code, message=$message, data=$data)"
    }
}

class TuShareDataVO : Serializable {
    var fields: List<String>? = null
    var items: List<List<String>>? = null
    override fun toString(): String {
        return "TuShareDataVO(fields=$fields, items=$items)"
    }
}