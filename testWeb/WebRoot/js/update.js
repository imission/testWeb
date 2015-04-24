/**
 * 新增保存功能，未解析checkbox...
 */


function submitUpdateItem(itemDivId) {

	var itemJson = buildItemValue(itemDivId);

	alert(itemJson);

	$.ajax({

		url : "/sample_test2/update",
		type : "POST",
		data : 'item=' + itemJson,
		success : function(mess) {

			alert("mess:" + mess);
		} // success
	});
}

/*
 * 组织item数据成json格式
 */
function buildItemValue(itemDivId) {

	// --- item基本信息 begin ---
	var id, item_num, time;

	var itemObj = $("#" + itemDivId);

	id = $(itemObj).children("[in=id]").val();
	item_num = $(itemObj).children("[in=item_num]").val();
	time = $(itemObj).children("[in=time]").val();
	// --- item基本信息 end ---

	var subitemsObj = $(itemObj).children("[in=subitemsObj]").get(0); // in=subitemsObj的元素
	var subitemObjs = $(subitemsObj).children("[in=subitemObj]"); // in=subitemObj的元素集合

	// --- 遍历in=subitemObj的元素集合，组织成subitem的json集合形式 begin ---
	var subitemsJson = "[";

	$(subitemObjs).each(

		function(index, ele) {

			// in=subitemObj的元素
			var subitemObj = ele;

			var id, check_id, item_num, sub_num, stype, value_type, value_str, value_multi;

			id = $(subitemObj).children("[sn=id]").val();
			check_id = $(subitemObj).children("[sn=check_id]").val();
			item_num = $(subitemObj).children("[sn=item_num]").val();
			sub_num = $(subitemObj).children("[sn=sub_num]").val();
			stype = $(subitemObj).children("[sn=stype]").val();
			value_type = $(subitemObj).children("[sn=value_type]").val();
			value_str = $(subitemObj).children("[sn=value_str]").val();

			buildMultiValue(subitemObj); // 组织value_multi
			value_multi = $(subitemObj)
					.children("[sn=value_multi]").val();
			if (value_multi == null) {
				value_multi = "''";
			}

			// --- 判断值 begin ---
			if (id == null || typeof (id) == 'undefined')
				id = 0;
			if (check_id == null || typeof (check_id) == 'undefined')
				check_id = 0;
			if (item_num == null || typeof (item_num) == 'undefined')
				item_num = "";
			if (sub_num == null || typeof (sub_num) == 'undefined')
				sub_num = "";
			if (stype == null || typeof (stype) == 'undefined')
				stype = "";
			if (value_type == null || typeof (value_type) == 'undefined')
				value_type = "";
			if (value_str == null || typeof (value_str) == 'undefined')
				value_str = "";
			if (value_multi == null || typeof (value_multi) == 'undefined')
				value_multi = "";
			// --- 判断值 end ---

			// 将值组织成json格式
			var json = "{";

			json += (
					"'id':'" + id + "'"
					+ ",'check_id':'" + check_id + "'"
					+ ",'item_num':'" + item_num + "'"
					+ ",'sub_num':'" + sub_num + "'" + ",'stype':'"
					+ stype + "'" + ",'value_type':'" + value_type
					+ "'" + ",'value_str':'" + value_str + "'"
					+ ",'value_multi':" + value_multi + "");

			json += "}";

			if ($(subitemObjs).length > index + 1) {
				json += ",";
			}

			// 将json格式的值添加到集合中
			subitemsJson += json;

		} //function
	); // each

	subitemsJson += "]";
	// --- 遍历in=subitemObj的元素集合，组织成subitem的json集合形式 end ---

	// --- 组织成单个item的最终数据 begin ---
	var itemJson = "{";

	itemJson += (
			"'id':'" + id + "'"
			+ ",'item_num':'" + item_num + "'"
			+ ",'time':'" + time + "'"
			+ ",'subitems':" + subitemsJson
		);

	itemJson += "}";
	// --- 组织成单个item的最终数据 end ---

	return itemJson;
}

/*
 * 组织组合值成json格式并放置到相应位置
 */
function buildMultiValue(subitemObj) {

	// 取值并组合成json的格式 begin
	var json = "\"{";

	var mvsObj = $(subitemObj).children("[sn=mvsObj]").get(0);

	var mvObjs = $(mvsObj).children("[sn_mv]");

	$(mvObjs).each(function(index, ele) {

		var mvObj = ele;

		var sn_mv_name = $(mvObj).attr("sn_mv");

		var sn_mv_val = $(mvObj).val();

		json += ("'" + sn_mv_name + "':'" + sn_mv_val + "'");

		if ($(mvObjs).length > index + 1) {
			json += ",";
		}

	}); // each

	json += "}\"";
	// 取值并组合成json的格式 end

	// 将组合好的值放置到相应位置
	$(subitemObj).children("[sn=value_multi]").val(json);

}// function
