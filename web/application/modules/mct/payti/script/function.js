//1、解决获取类名的兼容问题
function getclass(classname,father){
	father=father||document;
	if(father.getElementsByClassName){
        return father.getElementsByClassName(classname);
	}else{
		var newarr=[];
		var all=document.getElementsByTagName("*");
		for(var i=0;i<all.length;i++){
			if(all[i].className==classname){
				newarr.push(all[i]);
			}
		}return newarr;

	}
}
//2、解决获取样式的兼容函数
//obj:对象
//attr:属性
function getStyle(obj,attr){
	if(obj.currentStyle){
		return obj.currentStyle[attr];
	}else{
	   return getComputedStyle(obj,null)[attr];
	}
}
//3、获取元素的兼容函数
//"#" id   "." 类名  "a"
function $(selecter,father){
	if(typeof selecter=="string"){
		father=father||document;
		selecter=selecter.replace(/^\s|\s*$/g,"");
		if(selecter.charAt(0)=="."){//类名
	        return getclass(selecter.slice(1),father);
		}else if(selecter.charAt(0)=="#"){//ID
	        return document.getElementById(selecter.slice(1));
		}else if(/^[a-z]+\d*$/g.test(selecter)){//正则
	        return father.getElementsByTagName(selecter);
		}
	    
	}else if(typeof selecter=="function"){
		window.onload=function(){
			selecter();
		}
	}
	
}
// $("div");
//正则：一个定规则的表达式对象
// /cxcas/
//4、获取节点中的子节点
//father:父节点
//type:"a"子节点只有元素节点
//     "b"子节点只有元素节点与非空的文本节点
function getChilds(father,type){
	type=type||"a";
	var all=father.childNodes;
	var newarr=[];
	for(var i=0;i<all.length;i++){
		if(type=="a"){
			if(all[i].nodeType==1){
			newarr.push(all[i]);
		    }
	    }else if(type=="b"){//元素+非空的文本节点
		   if(all[i].nodeType==1||(all[i].nodeType==3&&all[i].nodeValue.replace(/^\s|\s*$/g,"")!="")){
			newarr.push(all[i]);
		    }

	      }
	}
		
	return newarr;
}
//5、获取第一个子节点
function getFirst(father){
	return getChilds(father)[0];
}
//6、获取最后一个子节点
function getLast(father){
	var i=getChilds(father).length-1;
	return getChilds(father)[i];
}
//7、获取指定的子节点
function getNum(father,num){
	return getChilds(father)[num];
}
//8、获取下一个兄弟结点的引用
function getNext(obj){
    var next=obj.nextSibling;
    if(!next){
            return false;
    	}
    while(next.nodeType==3||next.nodeType==8){
    	next=next.nextSibling
    	if(!next){
            return false;
    	}
    }
	return next;
}
//9、获取上一个兄弟节点的引用
function getpre(obj){
	var pre=obj.previousSibling;
	if(!pre){
            return false;
    	}
    while(pre.nodeType==3||pre.nodeType==8){
    	pre=pre.previousSibling;
    	if(!pre){
            return false;
    	}
    }
	return pre;
}
//10、把一个元素插入到某一个元素之后
function insertAfter(father,newobj,oldobj){
	var next=getNext(oldobj);
	if(next){
		father.insertBefore(newobj,next)
	}else{
		father.appendChild(newobj);
	}
}