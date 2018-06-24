function Calendar(){
	return {
		init: function(cfg){
			cfg = cfg || {};
			this.cfg = {
				lng : cfg.lng || 'ru',
				arrow_size: cfg.arrow_size || 9,
				tab: cfg.tab || 3,
				/*onSelect: function(){}*/
			};
			//this.elem = z(el);
			//this.name = el;
			this.old = [0,null,null,null];
			this.date = {year:-1, month:0, day: 0};
			this.dateObj = new Date();
			this.actTabNum = 1;
			//this.button = document.getElementById(button);
			this.monNames = this.cfg.lng == 'ru'	? ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь', 'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь']
													: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
			this.dayNames = this.cfg.lng == 'ru'	? ['Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб', 'Вс'] : ['Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa', 'Su'];
			// Создаем календари
			var zDobj = document.createElement('DIV');
			zDobj.className = 'calendar';
			zDobj.style.visibility = 'hidden';
			// 1 - дни, 2 - месяцы, 3 - годы, 4 - десятилетия, 5 - столетия
			// Создаем стрелки
			var arr = '';
			for	(var h = 1, w = 1; h <= this.cfg.arrow_size; h+=2, w++){
				arr = '<div style="height:' + h + 'px;width:' + w + 'px;">' + arr + '</div>';
			}

			zDobj.innerHTML = '<div class="cont"><table cellspacing="0" class="header"><tr><th class="arr_l">' + arr +'</th><th></th><th class="arr_r">' + arr +'</th></tr></table><div style="height:114px;overflow:hidden;"><div></div></div>';
			this.el = zDobj;
			this.hTab = zDobj.firstChild.firstChild;
			this.header = this.hTab.rows[0].cells[1];
			this.cTab = zDobj.firstChild.lastChild.firstChild;
			var str  = '<table cellspacing="0" class="days" id="zDTab1"><tr>';
			var str_d = '';
			for(var i = 0; i < 7; i++){
				str += '<th' + (i < 6 ? '' : ' class="we"') + '>' + this.dayNames[i] + '</th>';
				str_d += '<td></td>';
			}
			for(i = 0; i < 6; i++) str += '</tr><tr>' + str_d;
			var str_m = '</tr></table><table cellspacing="0" id="zDTab2">';
			var str_y = '</tr></table><table cellspacing="0" id="zDTab3">';
			for(var y = 0; y < 12; y++){
				str_y += '<td></td>';
				str_m += '<td>' + this.monNames[y].substr(0,3) + '</td>';
				if(y % 4 == 3) {
					str_y += '</tr><tr>';
					str_m += '</tr><tr>';
				}
			}
			this.cTab.innerHTML = str + str_m + str_y + '</tr></table>';

			this.tabs = this.cTab.childNodes;
			this.tabs[0].value = this.tabs[1].value = this.tabs[2].value = {html: 0, num: 0, row: 0, col: 0};
			this.tabs[0].style.display = this.tabs[2].style.display = 'none';
			this.actTab = this.tabs[1];
			document.getElementById('calend').appendChild(zDobj);
			this.cTab.parentNode.style.height = this.tabs[1].offsetHeight + 'px';
			zDobj.style.height = this.tabs[1].offsetHeight + 18 + 'px';
			var _t = this;
			this.el.onclick = function(e){
				e = e || window.event;
				if(e.stopPropagation) {
					e.stopPropagation();
					e.preventDefault();
				}
				else {
					e.returnValue = true;
					e.cancelBubble = true;
				}
			};
			this.cTab.onclick = function (e){
				e = e || window.event;
				var o = e.target || e.srcElement;
				var cell = 0, tab = 0;
				if(o.tagName == 'TD'){
					cell = 1 + o.cellIndex + (tab == 4 ? (o.parentNode.rowIndex-1) * 7 : o.parentNode.rowIndex * 4);
					if(!cell) return;
					if(o.offsetParent.old) o.offsetParent.old.className = o.offsetParent.old.className.replace(' act', '');
					o.offsetParent.old = o;
					o.offsetParent.value = {html: o.value, num: cell, row: o.parentNode.rowIndex, col: o.cellIndex};
					switch(_t.actTabNum){
						case 1: _t.date.day = o.value.d; _t.dateObj.setMonth(_t.date.month + o.value.m - 2);_t.dateObj.setDate(_t.date.day);break;
						case 2: _t.date.month = cell;_t.dateObj.setMonth(_t.date.month-1);break;
						case 3:
						case 4: _t.dateObj.setYear(o.value);
						case 5: _t.date.year = o.value;break;
					}
					_t.showTab(_t.actTabNum - 1);
				}

			};
			this.hTab.onclick = function (e){
				e = e || window.event;
				var o = e.target || e.srcElement;
				while(o.tagName == 'DIV'){
					o = o.parentNode;
				}
				if(o.tagName == 'TH'){
					if(o.className) {
						var a = o.className == 'arr_r' ? 1 : -1;
						switch(_t.actTabNum){
							case 1: _t.date.month += a;break;
							case 2: _t.date.year += a;break;
							case 3: _t.date.year += a*10;break;
							case 4: if(_t.date.year == -1) _t.date.year = 1900;_t.date.year += a*100;break;
							case 5: if(_t.date.year == -1) _t.date.year = 1000;_t.date.year += a*1000;break;
						}
						_t.showTab(_t.actTabNum);
					}
					else {
						if(_t.actTabNum > 4) return; 
						_t.showTab(_t.actTabNum + 1);
					}
				}

			};
		},
		add:function(el, button, cfg){
			var _this = this;
			var btn = z(button);
			btn.onclick = function(e){
				e = e || window.event;
				_this.show(el,button,cfg);
				if(e.stopPropagation) {
					e.stopPropagation();
					e.preventDefault();
				}
				else {
					e.returnValue = true;
					e.cancelBubble = true;
				}
			};
			return false;
		},
		show:function(el, button, cfg){
			var btn = z(button);
			this.elem = z(el);
			if(this.elem.disabled) return;
			var offset = this.offset(this.elem);
			cfg = cfg || {exit:1, start:4, format:'dd.mm.yyyy',year:-1, month:0, onSelect: function(){}};
			this.cfg.get = cfg.exit || 1;
			this.cfg.format = cfg.format || 'dd.mm.yyyy';
			cfg.start = cfg.start || 4;
			cfg.year = cfg.year || -1;
			cfg.month = cfg.month || 0;
			this.cfg.onSelect = cfg.onSelect || function(){};
			var d = new Date();
			this.el.style.left = document.body.scrollLeft +offset.left + 'px';
			this.el.style.top  = document.body.scrollTop + offset.top + 'px';
			
			this.date = {year:cfg.year || d.getFullYear(), month:cfg.month || d.getMonth()+1, day: 0};
			this.showTab(cfg.start);
			this.el.style.visibility = 'visible';
			var _this = this;
			document.onclick = function(){
				_this.el.style.visibility = 'hidden';
				document.onclick = null;
			};
		},
		showTab:function(num){
			if(num < this.cfg.get) {
				this.elem.value = this.format();
				//this.elem.focus();
				this.elem.dt = this.dateObj;
				if (this.cfg.onSelect) this.cfg.onSelect();
				this.el.style.visibility = 'hidden';
				return;
			}
			if(this.actTab && this.actTab.style) this.actTab.style.display = 'none';
			this.actTab = this.tabs[num > 2 ? 2 : num-1];
			this.actTabNum = num;
			this.actTab.style.display = 'block';
			// ��������
			switch(num){
				case 1:
					this.reMonth(this.date.year, this.date.month);
					break;
				case 2:
					this.header.innerHTML = this.date.year;
					break;
				case 3:
					this.reYears(this.date.year);
					break;
				case 4:
					this.reYears(this.date.year, 10);
					break;
				case 5:
					this.reYears(this.date.year, 100);
					break;

			}
			
		},
		reYears: function(y, type){
			type = type && (type == 10 || type == 100 || type == 1000) ? type : 1;
			y = y == -1 && type > 1 ? (type > 10 ? 1000 : 1910) : Math.floor(y / type / 10) * type * 10;
			var old = y % (type*10) == 0;
			this.header.innerHTML = old ? y + ' - ' + (y + 10 * type - 1) : (y - type) + ' - ' + (y + 11 * type - 1);
			y-=type;
			var t;
			for(var r = 0; r < 3; r++){
				for(var c = 0; c < 4; c++){
					if(y >= 0) {
						t = this.tabs[2].rows[r].cells[c];
						//t.innerHTML = type == 1 ? y : y + '<br>' + (y + type - 1);
						t.innerHTML = type == 1 ? y : (type == 10 ?  String(y).substr(0,String(y).length - 1) + 'x' : String(y).substr(0,String(y).length - 2) + 'xx' );
						t.value = y;
						t.className = (this.date.year  > 0 && y <= this.date.year && this.date.year < (y + type)) ? 'act' : '';
					}
					y+=type;
				}
			}
			this.tabs[2].rows[0].cells[0].className += old ? 'old' : '';
			this.tabs[2].rows[2].cells[3].className += old ? 'old' : '';
		},
		reMonth: function(year, mon){
			this.dateObj = new Date(year, mon-1, 1);
			var d = this.dateObj;
			var first = d.getDay();
			if(first == 0) first = 7;
			mon = d.getMonth() + 1;
			year = d.getFullYear();
			this.header.innerHTML = this.monNames[mon-1] + ' ' + year;
			this.date.year = year;
			this.date.month = mon;
			var days = [this.getMonthDays(mon-1, year), this.getMonthDays(mon, year), this.getMonthDays(mon+1, year)];
			var today = new Date();
			var t_mon = today.getMonth() + 1, t_year = today.getFullYear(), t_day = today.getDate();

			var day = 2 - first;
			if(day == 1) day = -6;
			var m = day > 0 ? 1 : 0;
			if(!m) day += days[0];
			for(var r = 1; r < 7; r++){
				for(var c = 0; c < 7; c++){
					this.tabs[0].rows[r].cells[c].innerHTML = day;
					this.tabs[0].rows[r].cells[c].value = {d: day, m:m};
					this.tabs[0].rows[r].cells[c].className = (m == 1 ? (c == 6 ? 'we' : '') + (t_year == year && t_mon == m && t_day == day ? ' act' : '') : 'old');
					if(day < days[m]) {
						day++;
					}
					else {
						day = 1;
						m++;
					}
				}
			}
		},

		getMonthDays: function(month, year){
			if(month < 1) {year--; month += 12;}
			else if(month > 12) {year++; month -= 12;}
			var daymonth = [31,31,28,31,30,31,30,31,31,30,31,30,31];
			if(month == 2)
				if((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) daymonth[2] = 29;
			return daymonth[month]; 
		},
		format: function(){
			var d = this.dateObj;
			var D = d.getDate(), M = d.getMonth(), Y = d.getFullYear();
			var f = {
				d: D,
				dd: this.zf(D),
				m: M + 1,
				mm:this.zf(M+1),
				yy:String(Y).substr(2),
				yyyy:Y
			};
			return this.cfg.format.replace(/(d{1,3}|m{1,3}|yyyy|yy)/g, function($1) {
				return f[$1];
			});
		},
		zf: function(num){
			return num < 10 ? '0' + num : num;
		},	
		offset: function(el){
			var top = el.offsetTop + el.offsetHeight + 1, left = el.offsetLeft + 2;
			while((el = el.offsetParent)) top += el.offsetTop - el.scrollTop, left += el.offsetLeft - el.scrollLeft;
			return {top: top, left: left};
		}
	};
}


function z(str){
	return document.getElementById(str);
}