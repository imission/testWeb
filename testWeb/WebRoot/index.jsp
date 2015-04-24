<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>index page</title>
	<script type="text/javascript" src="/testWeb/js/jquery-1.8.2-min.js"></script>
	<script type="text/javascript">

		function buildCheckbox() {

			var jsonStr = "{";
			jsonStr += "'usec':";
			jsonStr += "[";

			$("[sn_mv=usec]:checked").each(function(index, ele) {

				var jsonItem = "{";

					var key = $(this).attr("sn_mv");

					var name = "'value'";

					var value = $(this).val();

					jsonItem += name + ":'" + value + "'";

				jsonItem += "}";

				jsonStr += jsonItem;

				if ($("[sn_mv=usec]:checked").size() > index + 1) {
					jsonStr += ",";
				}

			});

			jsonStr += "]";
			jsonStr += "}";

	alert(jsonStr);
		}

		function setCheckbox() {

			var jsonStr = "{'usec':[{'value':'1'},{'value':'2'}]}";

			var valueJson = eval("(" + jsonStr + ")");

			var usecName = "usec";
			var usecValueArr = eval(valueJson["usec"]);

			for (var key in usecValueArr) {

				var usecValueItem = eval(usecValueArr[key]);

				var usecValue = usecValueItem["value"];

				$("[sn_mv=usec]").each(function(index, ele) {

					var value = $(this).val();

					if (usecValue == value) {
	
						$(this).attr("checked", true);
					}

				});
			}//for
		}

	</script>

	<script type="text/javascript">

		function buildRadio() {

			var jsonStr = "{";
			jsonStr += "'user':";
			jsonStr += "[";
	
			$("[sn_mv=user]:checked").each(function(index, ele) {

				var jsonItem = "{";

				var key = $(this).attr("sn_mv");

				var name = "'value'";

				var value = $(this).val();

				jsonItem += name + ":'" + value + "'";

				jsonItem += "}";

				jsonStr += jsonItem;

				if ($("[sn_mv=user]:checked").size() > index + 1) {
					jsonStr += ",";
				}

			});

			jsonStr += "]";
			jsonStr += "}";

			alert(jsonStr);
		}

		function setRadio() {

			var jsonStr = "{'user':[{'value':'1'}]}";

			var valueJson = eval("(" + jsonStr + ")");
	
			var usecName = "user";
			var usecValueArr = eval(valueJson["user"]);
	
			for (var key in usecValueArr) {
	
				var usecValueItem = eval(usecValueArr[key]);
	
				var usecValue = usecValueItem["value"];
	
				$("[sn_mv=user]").each(function(index, ele) {
	
					var value = $(this).val();
	
					if (usecValue == value) {
	
						$(this).attr("checked", true);
					}
	
				});
			}//for
	
		}
	</script>
	<script type="text/javascript">
	
		function test() {
			
			var name = $("[sn_mv=usec]")[0].tagName;
			
			alert(name);

			var type2 = $("[sn_mv=usec]").attr("type");
			
			alert(type2);
		}

	</script>
</head>
<body>

	<div sn="mvsObj">

		药物名称<br /> <select sn_mv="drugName" sub_num="W-B001-05">
			<option value='1'>赫赛汀（罗氏）</option>
			<option value='2'>替吉奥胶囊（大鹏）</option>
			<option value='3'>卡培他滨（赛诺菲）</option>
			<option value='4'>多西他赛（赛诺菲）</option>
		</select> 
		药量<input sn_mv="dosage" sub_num="W-B001-05" type="text" /><br />
		用药时段<input sn_mv="drugTime" sub_num="W-B001-05" type="text" /><br />
		用药周期<input sn_mv="drugCycle" sub_num="W-B001-05" type="text" /><br />

		用药checkbox
		<div sn_mv="usec" type="checkbox">
			<input type="checkbox" sn_mv="usec" sub_num="W-B001-05" name="usec" value="1" />药c1 
			<input type="checkbox" sn_mv="usec" sub_num="W-B001-05" name="usec" value="2" />药c2 <br />
		</div>
		用药radio
		<div sn_mv="user" type="radio">
			<input type="radio" sn_mv="user" sub_num="W-B001-05" name="user" value="1" />药r1 
			<input type="radio" sn_mv="user" sub_num="W-B001-05" name="user" value="2" /> 药r2
		</div>

	</div>

	<br />
	<br />
	<input type="button" value="build checkbox" onclick="buildCheckbox();" />
	<br />
	<input type="button" value="set checkbox" onclick="setCheckbox();" />

	<br />
	<br />
	<input type="button" value="build radio" onclick="buildRadio();" />
	<br />
	<input type="button" value="set radio" onclick="setRadio();" />

	<br />
	<br />
	<input type="button" value="test" onclick="test();" />
</body>
</html>