export default {
  // 根据id、class、tag获取第一个匹配到的值
  getDom(selector, type = "id") {
    const getDomBy = {
      id(selector) {
        return document.getElementById(selector);
      },
      className(selector) {
        return document.getElementsByClassName(selector)[0];
      },
      tagName(selector) {
        return document.getElementsByTagName(selector)[0];
      }
    };
    return getDomBy[type](selector);
  },
  // echarts的title样式
  defaultEchartsOpt: {
    title: {
      text: "",
      left: "center",
      top: 20,
      textStyle: {
        color: "#666"
      }
    }
  },
  insertStr(soure, start, newStr) {
    return soure.slice(0, start) + newStr + soure.slice(start);
  },
  //时间转换成字符串
  dateToString(date, fmt) {
    var o = {
      "Q+": Math.floor((date.getMonth() + 3) / 3), //季度
      "M+": date.getMonth() + 1, //月份
      "d+": date.getDate(), //日
      "H+": date.getHours() % 24 == 0 ? 0 : date.getHours() % 24, //小时
      "h+": date.getHours(), //小时
      "m+": date.getMinutes(), //分
      "s+": date.getSeconds(), //秒
      "f+": date.getMilliseconds() //毫秒
    };
    var week = {
      "0": "/u65e5",
      "1": "/u4e00",
      "2": "/u4e8c",
      "3": "/u4e09",
      "4": "/u56db",
      "5": "/u4e94",
      "6": "/u516d"
    };
    if (/(y+)/.test(fmt))
      fmt = fmt.replace(
        RegExp.$1,
        (date.getFullYear() + "").substr(4 - RegExp.$1.length)
      );
    if (/(E+)/.test(fmt))
      fmt = fmt.replace(
        RegExp.$1,
        (RegExp.$1.length > 1
          ? RegExp.$1.length > 2
            ? "/u661f/u671f"
            : "/u5468"
          : "") + week[date.getDay() + ""]
      );
    for (var k in o) {
      if (k == "f+") {
        if (new RegExp("(" + k + ")").test(fmt)) {
          var regExp0 = "000000000" + o[k];
          fmt = fmt.replace(
            RegExp.$1,
            regExp0.substr(regExp0.length - RegExp.$1.length)
          );
        }
      } else {
        if (new RegExp("(" + k + ")").test(fmt))
          fmt = fmt.replace(
            RegExp.$1,
            RegExp.$1.length == 1
              ? o[k]
              : ("00" + o[k]).substr(("" + o[k]).length)
          );
      }
    }
    return fmt;
  },
  // 获取每个月最后的一天
  monthLastDate(dateObj) {
    let nextMonth = dateObj.getMonth() + 1; //0-11，下一个月
    //设置当前日期为下个月的1号
    dateObj.setMonth(nextMonth);
    dateObj.setDate(1); //1-31
    let nextMonthFirstDayTime = dateObj.getTime(); //下个月一号对应毫秒
    let theMonthLastDayTime = nextMonthFirstDayTime - 24 * 60 * 60 * 1000; //下个月一号减去一天，正好是这个月最后一天
    return new Date(theMonthLastDayTime)
  },
  //将json格式时间转js格式时间
  jsonDateToString: function(value, format) {
    if (!value) {
      return "";
    }
    if (typeof value === "object") return DateToString(value, format);
    var flag = value.substr(0, 1);
    if (flag == "/") {
      /*json格式时间转js时间格式*/
      value = value.substr(1, value.length - 2);
      var obj = eval("(" + "{Date: new " + value + "}" + ")");
      var dateValue = obj["Date"];
      if (dateValue.getFullYear() < 1900) {
        return "";
      }
      return DateToString(dateValue, format);
    } else {
      return value;
    }
  },
  //将json格式时间转Chart格式时间
  jsonDateToChart: function(value) {
    value = JsonDateToString(value, "yyyy-MM-dd HH:mm:ss");
    if (value == "") {
      return value;
    }
    return new Date(value.replace(/-/g, "/")).getTime();
  },
  //将json格式时间转标准时间对象
  jsonDateToDate: function(value) {
    value = JsonDateToString(value, "yyyy-MM-dd HH:mm:ss");
    if (value == "") {
      return value;
    }
    return new Date(value.replace(/-/g, "/"));
  },
  //格式化数字(保留小数位数)
  roundNum: function(value, num) {
    if (value) {
      if (typeof value == "number") {
        var number = value.toFixed(num);
        if (value != 0) {
          return number;
        } else {
          var zero = "";
          for (var i = 0; i < num; i++) {
            zero += eval("0");
          }
          return "0." + zero;
        }
      } else {
        return "";
      }
    } else {
      return "";
    }
  },
  //格式化数字(保留有效数字)
  roundValid: function(value, length) {
    var number;
    if (value == null) {
      return number;
    } else if (value >= 1000 || value <= -1000) {
      var num = value.toString();
      var index = num.indexOf(".");
      if (index < 0) return num;
      else {
        number = num.substring(0, index);
        return number;
      }
    } else {
      number = value.toPrecision(length);
      return number;
    }
  },
  // 对象拷贝
  copyObject(obj) {
    var keys = Object.keys(obj);
    var result = {};
    for (var i = 0; i < keys.length; i++) {
      var key = keys[i];
      result[key] = obj[key];
    }
    return result;
  },
  // 对象数组都能拷贝
  deepCopy(obj) {
    // 只拷贝对象
    if (typeof obj !== 'object') return;
    // 根据obj的类型判断是新建一个数组还是一个对象
    var newObj = obj instanceof Array ? [] : {};
    for (var key in obj) {
      // 遍历obj,并且判断是obj的属性才拷贝
      if (obj.hasOwnProperty(key)) {
        // 判断属性值的类型，如果是对象递归调用深拷贝
        newObj[key] = typeof obj[key] === 'object' ? this.deepCopy(obj[key]) : obj[key];
      }
    }
    return newObj;
  },  
  isEmpty(obj){
    if(typeof obj == "undefined" || obj == null || obj == "" || obj =="null" || obj == undefined){
        return true;
    }else{
        return false;
    }
  }
};
/**
 * 按指定格式-格式化时间 如：new Date().Format("yyyy-MM-dd hh:mm:ss");
 * hasweek用来表示是否显示星期
 */
Date.prototype.Format = function(fmt, hasweek) {
  var weekday = [
    "星期日",
    "星期一",
    "星期二",
    "星期三",
    "星期四",
    "星期五",
    "星期六"
  ];
  var o = {
    "M+": this.getMonth() + 1,
    "d+": this.getDate(),
    "h+": this.getHours(),
    "m+": this.getMinutes(),
    "s+": this.getSeconds(),
    "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
    S: this.getMilliseconds() // 毫秒
  };
  if (/(y+)/.test(fmt))
    fmt = fmt.replace(
      RegExp.$1,
      (this.getFullYear() + "").substr(4 - RegExp.$1.length)
    );
  for (var k in o) {
    if (new RegExp("(" + k + ")").test(fmt))
      fmt = fmt.replace(
        RegExp.$1,
        RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length)
      );
  }

  return (
    fmt + (hasweek ? "&nbsp;&nbsp;&nbsp;&nbsp;" + weekday[this.getDay()] : "")
  );
};
