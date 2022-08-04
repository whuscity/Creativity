function getDocData(id){
    var xhttp = new XMLHttpRequest();
    xhttp.open('GET', 'http://192.168.24.233:9999/info/' + id , true);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.setRequestHeader('Access-Control-Allow-Origin', '*');
    xhttp.send();

    xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            var resp = xhttp.response;
            var data = JSON.parse(resp).data;
            var title = document.getElementById('title');
            var doi = document.getElementById('doi');
            var external_id = document.getElementById('external_id');
            var venue_str = document.getElementById('venue_str');
            var authors_name_str = document.getElementById('authors_name_str');
            var abstract_short = document.getElementById('abstract_short');
            var keywords = document.getElementById('keywords');
            var creativity_keywords = document.getElementById('creativity_keywords');
            var triple_keywords = document.getElementById('triple_keywords');
            title.innerHTML = data.title;
            doi.innerHTML = data.doi;
            external_id.innerHTML = "PMID:" + data.externalId;
            venue_str.innerHTML = data.venueStr || "";
            authors_name_str.innerHTML = data.authorsNameStr || "";
            abstract_short.innerHTML = data.abstractShort || "";
            keywords.innerHTML = data.keywordsStr || "";
            creativity_keywords.innerHTML = data.creativityWordsStr || "";
            triple_keywords.innerHTML = data.triplesStrList || "";
        }
    }
}


window.onload = function() {
    var params = window.location.search.split('?')[1];
    if (params.length > 0) {
        var pair = params.split('=');
        var key = decodeURIComponent(pair[0]);
        var val = decodeURIComponent(pair[1]);
        if (key === 'pmid') {
            getDocData(val);
        }
    }
    window.cited_orderType = "publish_year";
    window.citing_orderType = "publish_year";
    getCiting("publish_year",1);
    getCited("publish_year",1);
}

function getAbstractFull(){
    var params = window.location.search.split('?')[1];
    if (params.length > 0) {
        var pair = params.split('=');
        var key = decodeURIComponent(pair[0]);
        var val = decodeURIComponent(pair[1]);
        if (key === 'pmid') {
            var pmid = val;
        }
    }
    var xhttp = new XMLHttpRequest();
    xhttp.open('GET', 'http://192.168.24.233:9999/abstract/' + pmid , true);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.setRequestHeader('Access-Control-Allow-Origin', '*');
    xhttp.send();
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            var resp = xhttp.response;
            var data = JSON.parse(resp).data;
            var abstract_full = document.getElementById('abstract_full');
            var abstract_short = document.getElementById('abstract_short');
            abstract_full.innerHTML = data.abstractFull;
            abstract_full.parentElement.setAttribute('class', 'word active');
            abstract_short.parentElement.setAttribute('class', 'word');
            var structured_abstract = document.getElementsByClassName('structured_abstract')[0];
            structured_abstract.setAttribute('class', 'structured_abstract active');
        }
    }
}

function hideAbstractFull(){
    var abstract_full = document.getElementById('abstract_full');
    var abstract_short = document.getElementById('abstract_short');
    abstract_full.parentElement.setAttribute('class', 'word');
    abstract_short.parentElement.setAttribute('class', 'word active');
    var structured_abstract = document.getElementsByClassName('structured_abstract')[0];
    structured_abstract.setAttribute('class', 'structured_abstract');
}

function getCiting(orderType,current){
    var params = window.location.search.split('?')[1];
    if (params.length > 0) {
        var pair = params.split('=');
        var key = decodeURIComponent(pair[0]);
        var val = decodeURIComponent(pair[1]);
        if (key === 'pmid') {
            var pmid = val;
        }
    }
    var xhttp = new XMLHttpRequest();
    xhttp.open('GET', 'http://192.168.24.233:9999/citing/' + pmid + '?orderType=' + orderType + '&current=' + current , true);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.setRequestHeader('Access-Control-Allow-Origin', '*');
    xhttp.send();
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            var resp = xhttp.response;
            var data = JSON.parse(resp).data;
            var records = data.records;
            var table = document.getElementById('citing');
            table.innerHTML = "";
            if (data.total == 0){
                var prev = document.getElementById('citing_prev_page');
                var next = document.getElementById('citing_next_page');
                var pageInfo = document.getElementById('citing_pageInfo');
                pageInfo.innerHTML = "无记录";
                prev.setAttribute('class', 'disabled');
                next.setAttribute('class', 'disabled');
            }else{
                for (var i = 0; i < records.length; i++) {
                    var record = records[i];
                    var tr = document.createElement('tr');
                    tr.setAttribute('class', 'result');
                    var id = (current - 1)*10 + i + 1
                    var tds = "<td>" + id + "</td><td>" + record.title + "</td><td>" + record.creativityIndex + "</td><td>" + record.citeCount + "</td><td>" + record.publishYear + "</td>";
                    tr.innerHTML = tds;
                    table.appendChild(tr);
                    var btn = document.getElementById('citing_' + orderType);
                    btn.setAttribute('class', 'active');
                }
                var prev = document.getElementById('citing_prev_page');
                var next = document.getElementById('citing_next_page');
                var pageInfo = document.getElementById('citing_pageInfo');
                pageInfo.innerHTML = current + "/" + data.pages;
                if(current == 1){
                    prev.setAttribute('class', 'disabled');
                }else{
                    prev.setAttribute('class', '');
                    prev.setAttribute('onclick', 'citing_page(' + (current - 1) + ')');
                }
                if(current == data.pages){
                    next.setAttribute('class', 'disabled');
                }else{
                    next.setAttribute('class', '');
                    next.setAttribute('onclick', 'citing_page(' + (current + 1) + ')');
                }
            }
            
        }
    }
}

function getCited(orderType,current){
    var params = window.location.search.split('?')[1];
    if (params.length > 0) {
        var pair = params.split('=');
        var key = decodeURIComponent(pair[0]);
        var val = decodeURIComponent(pair[1]);
        if (key === 'pmid') {
            var pmid = val;
        }
    }
    var xhttp = new XMLHttpRequest();
    xhttp.open('GET', 'http://192.168.24.233:9999/ref/' + pmid + '?orderType=' + orderType + '&current=' + current , true);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.setRequestHeader('Access-Control-Allow-Origin', '*');
    xhttp.send();
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            var resp = xhttp.response;
            var data = JSON.parse(resp).data;
            var records = data.records;
            var table = document.getElementById('ref');
            table.innerHTML = "";
            if (data.total == 0){
                var prev = document.getElementById('ref_prev_page');
                var next = document.getElementById('ref_next_page');
                var pageInfo = document.getElementById('ref_pageInfo');
                pageInfo.innerHTML = "无记录";
                prev.setAttribute('class', 'disabled');
                next.setAttribute('class', 'disabled');
            }else{
                for (var i = 0; i < records.length; i++) {
                    var record = records[i];
                    var tr = document.createElement('tr');
                    tr.setAttribute('class', 'result');
                    var id = (current - 1)*10 + i + 1
                    var tds = "<td>" + id + "</td><td>" + record.title + "</td><td>" + record.creativityIndex + "</td><td>" + record.citeCount + "</td><td>" + record.publishYear + "</td>"
                    tr.innerHTML = tds;
                    table.appendChild(tr);
                    var btn = document.getElementById('cited_' + orderType);
                    btn.setAttribute('class', 'active');
                }
                var prev = document.getElementById('cited_prev_page');
                var next = document.getElementById('cited_next_page');
                var pageInfo = document.getElementById('cited_pageInfo');
                pageInfo.innerHTML = current + "/" + data.pages;
                if(current == 1){
                    prev.setAttribute('class', 'disabled');
                }else{
                    prev.setAttribute('class', '');
                    prev.setAttribute('onclick', 'cited_page(' + (current - 1) + ')');
                }
                if(current == data.pages){
                    next.setAttribute('class', 'disabled');
                }else{
                    next.setAttribute('class', '');
                    next.setAttribute('onclick', 'cited_page(' + (current + 1) + ')');
                }
            }
        }
    }
}

function reorder_citing(orderType) {
    var btn1 = document.getElementById('citing_creativity_index');
    var btn2 = document.getElementById('citing_cite_count');
    var btn3 = document.getElementById('citing_publish_date');
    btn1.setAttribute('class', '');
    btn2.setAttribute('class', '');
    btn3.setAttribute('class', '');
    getCiting(orderType,1);
    window.citing_orderType = orderType;
}

function reorder_cited(orderType) {
    var btn1 = document.getElementById('cited_creativity_index');
    var btn2 = document.getElementById('cited_cite_count');
    var btn3 = document.getElementById('cited_publish_date');
    btn1.setAttribute('class', '');
    btn2.setAttribute('class', '');
    btn3.setAttribute('class', '');
    getCited(orderType,1);
    window.cited_orderType = orderType;
}

function cited_page(i){
    var orderType = window.cited_orderType;
    getCited(orderType,i);
}

function citing_page(i){
    var orderType = window.citing_orderType;
    getCiting(orderType,i);
}