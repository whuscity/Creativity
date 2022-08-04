function getData(j){
    var xhttp = new XMLHttpRequest();
    xhttp.open('POST', 'http://192.168.24.233:9999/search', true);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.setRequestHeader('Access-Control-Allow-Origin', '*');
    xhttp.send(JSON.stringify(j));

    xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            document.body.scrollTop = 0;
            document.documentElement.scrollTop = 0;
            var resp = xhttp.response;
            var records = JSON.parse(resp).data.searchResultRecords;
            if (j.orderType) {
                var btn = document.getElementById('search_'+j.orderType);
                btn.className = "active";
            }
            var list = document.getElementById('list');
            list.innerHTML = "";
            if (JSON.parse(resp).data.hitTotal == 0){
                var info = document.getElementById('info');
                info.innerHTML = "共 0 条结果。";
                var pageInfo = document.getElementById('pageInfo');
                pageInfo.innerHTML = "无结果";
                var prev_page = document.getElementById('prev_page');
                var next_page = document.getElementById('next_page');
                prev_page.setAttribute('class', 'disabled');
                next_page.setAttribute('class', 'disabled');
            }else{
                for (i = 0; i < records.length; i++) {
                    var record = records[i];
                    var div = document.createElement('div');
                    div.setAttribute('class', 'result');
                    var text = "<div class=\"title\"><a href=\""+ "/document.html?pmid=" + record.externalId +"\"><h3>" + record.title + "</h3></a><input type=\"checkbox\"></div><div class=\"info\"><div class=\"author\"><span>" + record.authorsNameStr + "</span></div><div class=\"journal\"><span class=\"doctype\">" + record.documentType +  "</span><span class=\"venueName\">" + record.venueStr + "</span></div><div class=\"abstract\"><h4>摘要</h4><p>" + record.abstractShort + "</p></div><div class=\"creativity\"><h4>被引量</h4><span>" + record.citeCount + "</span><a href=\"#\">创新分析</a></div><span></span><div class=\"pubtime\"><span>"+ record.publishYear +"</span></div></div>"
                    div.innerHTML = text;
                    list.appendChild(div);
                }
                
                var info = document.getElementById('info');
                var startItem = (JSON.parse(resp).data.current - 1)*(JSON.parse(resp).data.size) + 1;
                var endItem = (JSON.parse(resp).data.current)*(JSON.parse(resp).data.size);
                info.innerHTML = "共 " + JSON.parse(resp).data.hitTotal + " 条结果，当前展示第 " + startItem + "-" + endItem  + " 条。";
                
                var totalPage = Math.ceil(JSON.parse(resp).data.hitTotal/JSON.parse(resp).data.size);
                var pageInfo = document.getElementById('pageInfo');
                pageInfo.innerHTML = JSON.parse(resp).data.current + "/" + totalPage;
                var prev_page = document.getElementById('prev_page');
                var next_page = document.getElementById('next_page');
                if (JSON.parse(resp).data.current == 1){
                    prev_page.setAttribute('class', 'disabled');
                }
                else {
                    prev_page.removeAttribute('class');
                    var prev = JSON.parse(resp).data.current - 1;
                    prev_page.setAttribute('onclick', 'page('+prev+')');
                }
                if (JSON.parse(resp).data.current == totalPage){
                    next_page.setAttribute('class', 'disabled');
                }
                else {
                    next_page.removeAttribute('class');
                    var next = JSON.parse(resp).data.current + 1;
                    next_page.setAttribute('onclick', 'page('+next+')');
                }
    
                var types = JSON.parse(resp).data.documentTypeRecords;
                var types_ul = document.getElementById('types_list');
                types_ul.innerHTML = "";
                for (i = 0; i < types.length; i++) {
                    var doctype = types[i]["key"];
                    var count = types[i]["count"];
                    var li = document.createElement('li');
                    li.innerHTML = "<input name=\"types\" type=\"checkbox\" value=\"" + doctype + "\"><label title=\"" + doctype + "(" + count +")\">" + doctype + " ("+ count + ")</label>";
                    types_ul.appendChild(li);
                }
    
                var venues = JSON.parse(resp).data.venueNameRecords;
                var venues_ul = document.getElementById('venues_list');
                venues_ul.innerHTML = "";
                for (i = 0; i < venues.length; i++) {
                    var venue = venues[i]["key"];
                    var count = venues[i]["count"];
                    var li = document.createElement('li');
                    li.innerHTML = "<input name=\"venues\" type=\"checkbox\" value=\"" + venue + "\"><label title=\"" + venue + "(" + count +")\">" + venue + " ("+ count + ")</label>";
                    venues_ul.appendChild(li);
                }
    
                var subfields = JSON.parse(resp).data.subfieldRecords;
                var subfields_ul = document.getElementById('subfields_list');
                subfields_ul.innerHTML = "";
                for (i = 0; i < subfields.length; i++) {
                    var subfield = subfields[i]["key"];
                    var count = subfields[i]["count"];
                    var li = document.createElement('li');
                    li.innerHTML = "<input name=\"subfields\" type=\"checkbox\" value=\"" + subfield + "\"><label title=\"" + subfield + "(" + count +")\">" + subfield + " ("+ count + ")</label>";
                    subfields_ul.appendChild(li);
                }
            }

            
        }
    }
}

function search(e) {
    var evt = window.event || e
    if (evt.keyCode == 13) {
        var query = document.getElementById("search").value;
        var url = "/search.html?q=" + query;
        window.open(url, '_self');
    }
}

function page(i) {
    q.current = i;
    getData(q);
}

function reorder(orderType){
    var btns = document.getElementsByName('reorder');
    for (i = 0; i < btns.length; i++) {
        var btn = btns[i];
        btn.removeAttribute('class');
    }
    q.orderType = orderType;
    q.current = 1;
    getData(q);
}

function timeFilter(){
    var start = document.getElementById("startPublishYear").value;
    var end = document.getElementById("endPublishYear").value;
    window.q = new queryObj(q.query,"relevance",1,10,1975,2022,[],[],[]);
    q.startPublishYear = start;
    q.endPublishYear = end;
    getData(q);
}

function typeFilter(){
    var documentType = [];
    var types = document.getElementsByName("types");
    for (i = 0; i < types.length; i++) {
        if (types[i].checked) {
            documentType.push(types[i].value);
        }
    }
    window.q = new queryObj(q.query,"relevance",1,10,1975,2022,[],[],[]);
    q.documentType = documentType;
    getData(q);
}

function venueFilter(){
    var venueName = [];
    var venues = document.getElementsByName("venues");
    for (i = 0; i < venues.length; i++) {
        if (venues[i].checked) {
            venueName.push(venues[i].value);
        }
    }
    window.q = new queryObj(q.query,"relevance",1,10,1975,2022,[],[],[]);
    q.venueName = venueName;
    getData(q);
}

function fieldFilter(){
    var subfield = [];
    var subfields = document.getElementsByName("subfields");
    for (i = 0; i < subfields.length; i++) {
        if (subfields[i].checked) {
            subfield.push(subfields[i].value);
        }
    }
    window.q = new queryObj(q.query,"relevance",1,10,1975,2022,[],[],[]);
    q.subfield = subfield;
    getData(q);
}

function queryObj(query,orderType,current,size,startPublishYear,endPublishYear,documentType,venueName,subfield) {
    this.query = query;
    this.orderType = orderType;
    this.current = current;
    this.size = size;
    this.startPublishYear = startPublishYear;
    this.endPublishYear = endPublishYear;
    this.documentType = documentType;
    this.venueName = venueName;
    this.subfield = subfield;
}

window.onload = function() {
    var params = window.location.search.split('?')[1];
    if (params.length > 0) {
        var param_list = params.split('&');
        window.q = new queryObj("","relevance",1,10,1975,2022,[],[],[]);
        for (var i = 0; i < param_list.length; i++) {
            var param = param_list[i].split('=');
            if (param[0] == 'q'){
                document.getElementById("search").value = decodeURI(param[1]);
                q.query = decodeURI(param[1]);
            }
        }
        getData(q);
    }
}