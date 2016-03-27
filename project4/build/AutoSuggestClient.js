// Create By: Zhanwei Ye & Hanjing Fang
// Date: 2-26-2016
// Name: AutoSuggestClient
// Description: A client issues requests to my proxy as the user types and shows a list of suggestions

// Constructor for AutoSuggestControl
function AutoSuggestControl(oTextbox) {
	this.cur = -1;
	this.layer = null;
    this.textbox = oTextbox;
    this.xmlHttp = new XMLHttpRequest();
    this.init();
}

// select a certain range of a text
AutoSuggestControl.prototype.selectRange = function (iStart, iLength) {
    if (this.textbox.createTextRange) {
        var oRange = this.textbox.createTextRange(); 
        oRange.moveStart("character", iStart); 
        oRange.moveEnd("character", iLength - this.textbox.value.length); 
        oRange.select();
    } else if (this.textbox.setSelectionRange) {
        this.textbox.setSelectionRange(iStart, iLength);
    }

    this.textbox.focus(); 
};

// to fill the textbox using the first suggestion
AutoSuggestControl.prototype.typeAhead = function (sSuggestion) {
    if (this.textbox.createTextRange || this.textbox.setSelectionRange) {
        this.isAhead = false;
        var iLen = this.textbox.value.length; 
        this.textbox.value = sSuggestion; 
        this.selectRange(iLen, sSuggestion.length);

    }
};

// assign the first suggestion to typeAhead
AutoSuggestControl.prototype.autosuggest = function (oThis,bTypeAhead) {

    return function () 
    {
        if (oThis.xmlHttp.readyState == 4) 
        {
            var aSuggestions = [];
            // get the CompleteSuggestion elements from the response
            if( oThis.xmlHttp.responseXML != null)
            {
                var s = oThis.xmlHttp.responseXML.getElementsByTagName('CompleteSuggestion');
            
                // construct a suggestion array
                for (i = 0; i < s.length; i++) 
                {
                    var text = s[i].childNodes[0].getAttribute("data");
                
                    aSuggestions.push(text);
                }
            }
            //make sure there's at least one suggestion
            if (aSuggestions.length > 0) 
            { 
                if (bTypeAhead) {
                    oThis.typeAhead(aSuggestions[0]);
                }   
                oThis.showSuggestions(aSuggestions);
            } 
            else 
            {
                oThis.hideSuggestions();
            }
        }
    }
};

// handleKeyUp for characters
AutoSuggestControl.prototype.handleKeyUp = function (oEvent) {

    var iKeyCode = oEvent.keyCode;

    if (iKeyCode == 8 || iKeyCode == 46) {
        this.requestSuggestions(this, false);

    } else if (iKeyCode < 32 || (iKeyCode >= 33 && iKeyCode <= 46) || (iKeyCode >= 112 && iKeyCode <= 123)) {
        //ignore
    } else {
        this.requestSuggestions(this, false);
    }
};

AutoSuggestControl.prototype.init = function () {

    var oThis = this;

    this.textbox.onkeyup = function (oEvent) {
        if (!oEvent) {
            oEvent = window.event;
        } 

        oThis.handleKeyUp(oEvent);
    };

    this.textbox.onkeydown = function (oEvent) {

        if (!oEvent) {
            oEvent = window.event;
        } 

        oThis.handleKeyDown(oEvent);
    };

    this.textbox.onblur = function () {
        oThis.hideSuggestions();
    };

    this.createDropDown();
};

AutoSuggestControl.prototype.requestSuggestions = function (oAutoSuggestControl,bTypeAhead) {
    var aSuggestions = [];
    var sTextboxValue = oAutoSuggestControl.textbox.value;

    if (sTextboxValue.length > 0){
        var request = "suggest?q="+encodeURI(sTextboxValue);
        oAutoSuggestControl.xmlHttp.open("GET", request); 
        oAutoSuggestControl.xmlHttp.onreadystatechange = oAutoSuggestControl.autosuggest(oAutoSuggestControl, bTypeAhead); 
        oAutoSuggestControl.xmlHttp.send(null);
    }
    else
    {
        this.hideSuggestions();
    }
};

//  hide the dropdown list after it has been shown
AutoSuggestControl.prototype.hideSuggestions = function () {
    this.layer.style.visibility = "hidden";
};

// highlight the current suggestion in the dropdown list
AutoSuggestControl.prototype.highlightSuggestion = function (oSuggestionNode) {
    for (var i=0; i < this.layer.childNodes.length; i++) {
        var oNode = this.layer.childNodes[i];
        if (oNode == oSuggestionNode) {
            oNode.className = "current"
        } else if (oNode.className == "current") {
            oNode.className = "";
        }
    }
};

// create the dropdown list and handle mousedown, mouseup and mouseover event
AutoSuggestControl.prototype.createDropDown = function () {

    this.layer = document.createElement("div");
    this.layer.className = "suggestions";
    this.layer.style.visibility = "hidden";
    this.layer.style.width = this.textbox.offsetWidth;
    document.body.appendChild(this.layer);

    //more code to come
    var oThis = this;

    this.layer.onmousedown = this.layer.onmouseup = 
    this.layer.onmouseover = function (oEvent) {
        oEvent = oEvent || window.event;
        oTarget = oEvent.target || oEvent.srcElement;

        if (oEvent.type == "mousedown") {
            oThis.textbox.value = oTarget.firstChild.nodeValue;
            oThis.hideSuggestions();
        } else if (oEvent.type == "mouseover") {
            oThis.highlightSuggestion(oTarget);
        } else {
            oThis.textbox.focus();
        }
    };
};

AutoSuggestControl.prototype.getLeft = function () {

    var oNode = this.textbox;
    var iLeft = 0;

    while(oNode.tagName != "BODY") {
        iLeft += oNode.offsetLeft;
        oNode = oNode.offsetParent; 
    }

    return iLeft;
};

AutoSuggestControl.prototype.getTop = function () {

    var oNode = this.textbox;
    var iTop = 0;

    while(oNode.tagName != "BODY") {
        iTop += oNode.offsetTop;
        oNode = oNode.offsetParent; 
    }

    return iTop;
};

AutoSuggestControl.prototype.showSuggestions = function (aSuggestions) {

    var oDiv = null;
    this.layer.innerHTML = "";

    for (var i=0; i < aSuggestions.length; i++) {
        oDiv = document.createElement("div");
        oDiv.appendChild(document.createTextNode(aSuggestions[i]));
        this.layer.appendChild(oDiv);
    }

    this.layer.style.left = this.getLeft() + "px";
    this.layer.style.top = (this.getTop()+this.textbox.offsetHeight) + "px";
    this.layer.style.visibility = "visible";
};

AutoSuggestControl.prototype.nextSuggestion = function () {
    var cSuggestionNodes = this.layer.childNodes;

    if (cSuggestionNodes.length > 0 && this.cur < cSuggestionNodes.length-1) {
        var oNode = cSuggestionNodes[++this.cur];
        this.highlightSuggestion(oNode);
        this.textbox.value = oNode.firstChild.nodeValue; 
    }
};

AutoSuggestControl.prototype.previousSuggestion = function () {
    var cSuggestionNodes = this.layer.childNodes;

    if (cSuggestionNodes.length > 0 && this.cur > 0) {
        var oNode = cSuggestionNodes[--this.cur];
        this.highlightSuggestion(oNode);
        this.textbox.value = oNode.firstChild.nodeValue; 
    }
};

AutoSuggestControl.prototype.handleKeyDown = function (oEvent) {
    switch(oEvent.keyCode) {
        case 38: //up arrow
            this.previousSuggestion();
            break;
        case 40: //down arrow 
            this.nextSuggestion();
            break;
        case 13: //enter
            this.hideSuggestions();
            break;
    }
};

