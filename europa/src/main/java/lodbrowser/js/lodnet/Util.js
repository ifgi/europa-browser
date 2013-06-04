
function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
}

function urlLastPart(url){
	var res;
	var posSlash = url.lastIndexOf("/");
	var posHash = url.lastIndexOf("#");
	if(posSlash > posHash){
		res = url.substring(posSlash + 1);
	}else if(posSlash < posHash){
		res = url.substring(posHash + 1);
	}else{
		res = url;
	}
	return res;
}

function sendSparqlQuery(query, baseURL, defGraphUri) {
	var format = "application/sparql-results+json";//var format = "application/json";
	var debug = "on";
	var timeout = 0;
	var params={
		"default-graph-uri": defGraphUri, "should-sponge": "soft", "query": query,
		"debug": debug, "timeout": timeout, "format": format,
		"save": "display", "fname": ""
	};
	
	var querypart="";
	for(var k in params) {
		querypart+=k+"="+encodeURIComponent(params[k])+"&";
	}
	var queryURL=baseURL + '?' + querypart;
	if (window.XMLHttpRequest) {
		xmlhttp=new XMLHttpRequest();
	}else{
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}

	var res;
	//try{
		xmlhttp.open("GET",queryURL,false);
		xmlhttp.send();
		res = JSON.parse(xmlhttp.responseText);
	/*}catch(err){
		alert(err);
	}*/
	return res;
}

function queryMask(subject, query, baseURL, defGraphUri, separator){

	var resjson = sendSparqlQuery(query, endpointUrl, defGraphUri);
	var res = new Array();
	for (var i = 0; i < resjson.results.bindings.length; i++){
		var s;
		if(resjson.hasOwnProperty(s)){
			s = urlLastPart(decodeURIComponent(resjson.results.bindings[i].s.value));
		}else{
			s = urlLastPart(decodeURIComponent(subject));
		}
		var p = urlLastPart(decodeURIComponent(resjson.results.bindings[i].p.value));
		var o = urlLastPart(decodeURIComponent(resjson.results.bindings[i].o.value));
		var obj = new Object();
		obj.source = s;
		obj.target = p + separator + o;
		obj.type = p;
		res.push(obj);
	}
	return res;
}