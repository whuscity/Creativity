function search(e) {
    var evt = window.event || e
    if (evt.keyCode == 13) {
        var search = document.getElementById("search").value;
        var url = "/search.html?q=" + search;
        window.open(url, '_blank');
    }
}