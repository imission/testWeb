package servlet;


import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.MultipartRequest;
import com.jfinal.upload.UploadFile;
import com.mysql.jdbc.Util;
import com.yckj.pojo.Attachment;
import com.yckj.pojo.CancerType;
import com.yckj.pojo.LymphNode;
import com.yckj.pojo.Operation;
import com.yckj.pojo.OperationInfo;
import com.yckj.tools.ActionUtil;
import com.yckj.tools.StringUtils;
/**
 * 癌症类型
 * @author yyy
 *
 */
public class CancerTypeAction extends ActionUtil{
	
	/**
	 * 癌症类型
	 * @author yyy
	 * @Date 2015
	 */
	public void index(){
		int p = getParaToInt("p", 1);
		String type = getPara("type");
		String select = "select * " ;
		String paras = "";
		int pageSize = 20;
		String sqlExceptSelect = " from cancer_type where 1=1 ";
		if (!StringUtils.isEmptyOrWhitespaceOnly(type)) {
			paras += "&cancer_name=" + type;
			sqlExceptSelect += " and  cancer_name like '%" + type + "%'";
			setAttr("cancer_name", type);
		}
		sqlExceptSelect += " order by cancer_id asc";
		Page<CancerType> lists=CancerType.cancertype.paginate(p, pageSize, select, sqlExceptSelect);
		Object obj  = getAttr("cancer") ;
		if(obj == null || obj == ""){
			setAttr("cancer", "");
		}
		setAttr("urlParas", paras);
		setAttr("list",lists );
		setAttr("totalRow", lists.getTotalRow());
		render("cancertypelist.html");	
	}
	
	//新增,修改
	public void savecancer(){
		String cancerid =  getPara("id");
		String cancername =  getPara("cancername");
		String cancerprefix=  getPara("cancerprefix");
		if(cancerid == ""){
			cancerid= null ;
		}
		CancerType cancer = new CancerType();
		cancer.set("cancer_id", cancerid);
		cancer.set("cancer_name", cancername);
		cancer.set("cancer_prefix", cancerprefix);
		if(cancerid != null){
			if(cancer.update()){
				index();
			}else{
				setAttr("Error", "保存失败！");	
				render("admin/msg.html");	
			}
		}else{
			if(cancer.save()){
				index();				
			}else{
				setAttr("Error", "保存失败！");	
				render("admin/msg.html");	
			}
		}
	}
	
	//删除
	public void delcancer(){
		String cancerid =  getPara("id");
		boolean isdel  = CancerType.cancertype.deleteById(cancerid);
		if(isdel){
			index();
		}else{
			setAttr("Error", "删除失败！");	
			render("admin/msg.html");
		}
	}
	
	
	/*****************淋巴结部分*********************/
	
	/**
	 * 淋巴结清扫
	 * @author yyy
	 * @Date 2015
	 */
	public void lymphlist(){
		List<CancerType> cancertype=CancerType.cancertype.find("select * from cancer_type ");
		int p = getParaToInt("p", 1);
		String typeid = getPara("typeid");
		if(typeid==""){
			typeid=null;
		   setAttr("cancer_id", "");
		}
		String select = "select * " ;
		String paras = "";
		int pageSize = 20;
		String sqlExceptSelect = "from lymph_node l join cancer_type c on l.cancer_id = c.cancer_id where 1=1";
		if (!StringUtils.isEmptyOrWhitespaceOnly(typeid)) {
			paras += "&c.cancer_id=" + typeid;
			sqlExceptSelect += " and  c.cancer_id = '" +typeid+"'";
		}
		sqlExceptSelect += " order by id asc";
		Page<LymphNode> lists=LymphNode.lymphnode.paginate(p, pageSize, select, sqlExceptSelect);
		Object obj  = getAttr("lymphnode") ;
		if(obj == null || obj == ""){
			setAttr("lymphnode", "");
			setAttr("cancer", "");
		}
		setAttr("urlParas", paras);
		setAttr("list",lists );
		setAttr("cancertypes",cancertype);
		setAttr("totalRow", lists.getTotalRow());
		render("lymphnodelist.html");
	}
	
	
	//新增,修改淋巴结
		public void savelymphnode(){
			String id =  getPara("id");
			String cancerid =  getPara("cancerid");
			String name=  getPara("name");
			String order=  getPara("order");
			if(id == ""){
				id= null ;
			}
			LymphNode lymphnode = new LymphNode();
			lymphnode.set("id", id);
			lymphnode.set("cancer_id", cancerid);
			lymphnode.set("lymph_name", name);
			lymphnode.set("lymph_order", order);
			if(id != null){
				if(lymphnode.update()){
					lymphlist();
				}else{
					setAttr("Error", "保存失败！");	
					render("admin/msg.html");	
				}
			}else{
				if(lymphnode.save()){
					lymphlist();			
				}else{
					setAttr("Error", "保存失败！");	
					render("admin/msg.html");	
				}
			}
		}
		
	
	//删除淋巴结
	public void dellympnode(){
		String id =  getPara("id");
		boolean isdel  = LymphNode.lymphnode.deleteById(id);
		if(isdel){
			lymphlist();
		}else{
			setAttr("Error", "删除失败！");	
			render("admin/msg.html");
		}
	}
	
	
	/*****************手术记录字典表************************/
	
	
	/**
	 * 字典表1
	 * @author yyy
	 * @Date 2015
	 */
	public void operationlist(){
		int p = getParaToInt("p", 1);
		String name = getPara("opername");
		String select = "select * " ;
		String paras = "";
		int pageSize = 20;
		String sqlExceptSelect = " from d_operation where 1=1 ";
		if (!StringUtils.isEmptyOrWhitespaceOnly(name)) {
			paras += "&operation_name=" + name;
			sqlExceptSelect += " and  operation_name like '%" + name + "%'";
			setAttr("operation_name", name);
		}
		sqlExceptSelect += " order by id asc";
		Page<Operation> lists=Operation.operation.paginate(p, pageSize, select, sqlExceptSelect);
		Object obj  = getAttr("operation") ;
		if(obj == null || obj == ""){
			setAttr("operation", "");
		}
		setAttr("urlParas", paras);
		setAttr("list",lists );
		setAttr("totalRow", lists.getTotalRow());
		render("operationlist.html");	
	}
	
	
	//删除字典1
		public void deloperation(){
			String id =  getPara("id");
			boolean isdel  = Operation.operation.deleteById(id);
			if(isdel){
				operationlist();
			}else{
				setAttr("Error", "删除失败！");	
				render("admin/msg.html");
			}
		}
		
	//修改,添加字典1
	  public void saveoperation(){
		  String id =  getPara("id");
			String name =  getPara("name");
			String num=  getPara("num");
			if(id == ""){
				id= null ;
			}
			Operation operation = new Operation();
			operation.set("id", id);
			operation.set("operation_name", name);
			operation.set("operation_num", num);
			if(id != null){
				if(operation.update()){
					operationlist();
				}else{
					setAttr("Error", "保存失败！");	
					render("admin/msg.html");	
				}
			}else{
				if(operation.save()){
					operationlist();			
				}else{
					setAttr("Error", "保存失败！");	
					render("admin/msg.html");	
				}
			}
	  }
	  
	  /**
		 * 字典表2
		 * @author yyy
		 * @Date 2015
	 */
	public void operinfolist(){
		List<CancerType> cancertype=CancerType.cancertype.find("select * from cancer_type ");
		int p = getParaToInt("p", 1);
		String itemnum = getPara("itemnum");
		String typeid = getPara("typeid");
		String infoname = getPara("infoname");
		setAttr("itemnum", itemnum);
		if(typeid==""){
			typeid=null;
		   setAttr("cancer_id", "");
		}
		String select = "select i.* ,c.cancer_name cname,a.attachment_url url" ;
		String paras = "";
		int pageSize = 20;
		String sqlExceptSelect = "  from d_operation_info i join d_operation o on i.operation_num = o.operation_num join cancer_type c on c.cancer_id=i.cancer_id join attachment a on i.attachment_id = a.attachment_id where i.operation_num = '"+itemnum+"'";
		if (!StringUtils.isEmptyOrWhitespaceOnly(typeid)) {
			paras += "&c.cancer_id=" + typeid;
			sqlExceptSelect += " and  c.cancer_id = '" +typeid+"'";
			setAttr("cancer_id", typeid);
		}
		if (!StringUtils.isEmptyOrWhitespaceOnly(infoname)) {
			paras += "&i.info_name=" + infoname;
			sqlExceptSelect += " and  i.info_name like '%" +infoname+"%'";
			setAttr("infoname", infoname);
		}
		sqlExceptSelect += " order by i.id asc ";
		Page<OperationInfo> lists=OperationInfo.operinfo.paginate(p, pageSize, select, sqlExceptSelect);
		
		setAttr("urlParas", paras);
		setAttr("list",lists );
		setAttr("cancertypes",cancertype);
		setAttr("totalRow", lists.getTotalRow());
		render("operation_infolist.html");	
	}  
	

	//删除字典二
		public void deloperinfo(){
			String id =  getPara("id");
			boolean isdel  = OperationInfo.operinfo.deleteById(id);
			System.out.println("delete   " + isdel);
			if(isdel){
				operinfolist();
			}else{
				setAttr("Error", "删除失败！");	
				render("admin/msg.html");
			}
		}
		
		//编辑用户信息跳转方法
	  	public void addoperinfo(){
	  		OperationInfo operinfo = new OperationInfo() ;
	  		Attachment att = new Attachment();
	  		String id = getPara("id");
			String itemnum = getPara("itemnum");
			setAttr("itemnum", itemnum);
			List<CancerType> cancertype=CancerType.cancertype.find("select * from cancer_type ");
	  		if(id != null){
	  			operinfo = OperationInfo.operinfo.findById(id);
	  			att = Attachment.attachment.findById(operinfo.get("attachment_id"));
	  			setAttr("operinfo", operinfo);
	  			setAttr("attachment", att);
	  		} else{
	  			setAttr("operinfo", "");
	  			setAttr("attachment", "");
	  			System.out.println("id----------"+id);
	  		}
	  		setAttr("cancertypes", cancertype);
	  		render("addoperation_infolist.html");	
	  	}
		
		//修改,添加字典2
		  public void saveoperinfo(){
			  UploadFile file = getFile("info_imgpath");
			  String attid = getPara("attid");
			  String id =  getPara("id");
			  String itemnum =  getPara("itemnum");
			  String typeid=  getPara("cancer_id");
			  String name=  getPara("info_name");
			  String order=  getPara("info_order");
				Attachment att = new Attachment();

				if(id.length()==0){
					id= null ;
				}
				if(attid.length()==0){
					attid= null ;
				}

			  if(file != null){
				  String fileName = file.getFileName();
				  //截取文件后缀
				  String type = fileName.substring(fileName.lastIndexOf("."));
				  String atttype = "";
				  //得到文件大小
				  Double length = (double) file.getFile().length();
				  //单位
				  String unit = "B" ;
				 if(type.equals(".jpg")||type.equals(".gif")||type.equals(".png")){
					 atttype = "图片" ;
				 }else{
					 atttype = "文件" ;
				 }
				 if(length >= 1024){
					 unit ="KB";
					   length = length /1024 ;
					   //转换大小
					   BigDecimal bg = new BigDecimal(length)  ;
					   length = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				 }
				  att.set("attachment_size", length+unit);
					att.set("attachment_type", atttype);
				  att.set("attachment_url",file.getFileName());

			  }else{
					Attachment attach = new Attachment();
					attach = Attachment.attachment.findById(id);
					att.set("attachment_size",attach.get("attachment_size"));
					att.set("attachment_type", attach.get("attachment_type"));
					att.set("attachment_name", attach.get("attachment_url"));
			  }
			 
				att.set("attachment_id", attid);
				att.set("attachment_name",name);
				
				OperationInfo operinfo = new OperationInfo();
				operinfo.set("id", id);
				operinfo.set("cancer_id", typeid);
				operinfo.set("operation_num", itemnum);
				operinfo.set("info_name", name);
				operinfo.set("info_order", order);
				operinfo.set("attachment_id", att.get("attachment_id"));
				if(id != null){
					if(att.update()){
						operinfo.update();
						operinfolist();
					}else{
						setAttr("Error", "保存失败！");	
						render("admin/msg.html");	
					}
				}else{
					if(att.save()){
						operinfo.set("attachment_id", att.get("attachment_id"));
						operinfo.save();
						operinfolist();	
					}else{
						setAttr("Error", "保存失败！");	
						render("admin/msg.html");	
					}
				}
		  }
		  
		  public void ajaxupload(){
			  System.out.println("文件上传");
			  UploadFile file = getFile("info_imgpath");
			  renderHtml("/upload/"+file.getFileName());
			  
		  }
		  

		  
		  
}
