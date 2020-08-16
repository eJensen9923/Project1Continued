userL = null;
userP = 0;
account = null;
balance = null;
livepage = -1;
selectedT = null;
function login() {
    let uname = document.getElementById("username").value;
    let pd = document.getElementById("password").value;
    
    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            document.getElementById("htmlbody").innerHTML = this.response;
            let cval = document.cookie;
            console.log(cval);
            cval = cval.substring(5);
            console.log(cval);
            cval = atob(cval);
            console.log(cval);
            userL = JSON.parse(cval);
            console.log(userL);
            console.log(userL.username);
            document.getElementById("banner").innerHTML = `Welcome: ${userL.username}`;
            loadpage("home.do");
        }
        if(this.readyState == 4 && this.status == 401) {
            document.getElementById("loginmessage").innerHTML = "Invalid credentials";
        }
    }
    xhttp.open("POST","http://localhost:8080/TRMS/login.do",true);

    xhttp.setRequestHeader('Content-Type','application/json');

    let userLogin = {
        username : uname,
        password : pd
    };
        
    xhttp.send(JSON.stringify(userLogin));
}

function loadpage(page) {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            document.getElementById("navbar").innerHTML = this.responseText;
            document.getElementById("display").innerHTML = "";
        }
    }

    xhttp.open("POST",`http://localhost:8080/TRMS/${page}`);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(JSON.stringify(userL));
}

function logout() {
    console.log("logout");
    let c = JSON.stringify(userL);
    c = btoa(c);
    document.cookie = `user=${c}; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
    location.reload();
}

function loaddiv(div) {
    console.log(div);
    switch(div) {
        case "request":
            loadrequestpage();
            break;
        case "pending":
            loadpendingpage();
            break;
        case "dept":
            loaddeptpage();
            break;
        case "benco":
            loadbencopage();
            break;
        case "newyear":
            getNewYearForm();
            break;
    }
}

function loadrequestpage() {
    livepage = 0;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            console.log("requestpageget");
            document.getElementById("display").innerHTML = this.responseText;
            getaccount();
        }
    }
    xhttp.open("GET", "http://localhost:8080/TRMS/loadaccountpage.do");
    xhttp.send();

    
}

function getaccount() {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            console.log("gettingaccount")
            console.log(this.responseText);
            account = JSON.parse(this.responseText);
            let i = 0;
            for (const ledger of account) {
                console.log(ledger);
                document.getElementById("requests").innerHTML += `<table id='t${i}' class="table table-striped"><tr><th>${ledger.fiscalYear}</th><th id='b${ledger.fiscalYear}'>${balance}</th><th>Status</th><th>Docs</th><th>Delete</th><th>Request Reimbursement</th></tr></table>`;
                let j = 0;
                balance = 0;
                for (const transaction of ledger.transactions) {
                    let approved = 'disabled';
                    let paid = 'disabled';
                    console.log(transaction);
                    balance += transaction.amount;
                    document.getElementById(`b${ledger.fiscalYear}`).innerHTML = balance;
                    if(transaction.Status == 2) {
                        approved = ''
                    }
                    if(transaction.Status < 4) {
                        paid = '';
                    }
                    document.getElementById(`t${i}`).innerHTML += `<tr id='r${j}'><td id='tID${j}'>${transaction.tID}</td><td>${transaction.amount}</td><td>${transaction.Status}</td><td><button onclick='docspage(${transaction.tID}, true)'>Docs</button></td><td><button onclick='deletetransaction(${transaction.tID}, true)' ${paid}>Delete</button></td><td><button onclick='approve(${transaction.tID});' ${approved}>Reimburse</button></td></tr>`;
                    j++;
                }
                i++;
            }

        }
    }
    xhttp.open("POST",`http://localhost:8080/TRMS/getaccount.do`);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(JSON.stringify(userL));
}

function populate(transactions, isbenco) {
    document.getElementById("pending").innerHTML = '<tr><th>Employee ID</th><th>Transaction</th><th>Amount</th><th>Start Date</th><th>Approve</th><th>Reject</th><th>Docs</th><th>Adjust</th></tr>';
    for (const transaction of transactions) {
        let adjustable = "disabled";
        if(isbenco) {
            adjustable = "";
        }
        //yyyy-mm-dd
        console.log(transaction);
        let datestring = "1999-12-31".split("-");

        if(transaction.startdate) {
            datestring = transaction.startdate.split("-");
        }
        
        let urgent = '';
        let startdate = new Date();
        let currentDate = new Date();
        startdate.setFullYear(datestring[0], datestring[1] - 1,datestring[2]);
        let timeremaining = startdate - currentDate;
        if(timeremaining < 1209600000) {
            urgent = "class='urgent'";
        }
        document.getElementById("pending").innerHTML +=
        `<tr ${urgent}><td>${transaction.empID}</td><td>${transaction.tID}</td><td>${transaction.amount}</td><td id="${transaction.startdate}">${transaction.startdate}</td><td><button onclick="approve(${transaction.tID})">Approve</button></td><td><button onclick="rejecttransaction(${transaction.tID})">Reject</button></td><td id="adjustinput${transaction.tID}"><button onclick="docspage(${transaction.tID}, false)">Docs</button></td><td><button ${adjustable} onclick="adjust(${transaction.tID})">Adjust</button></td></tr>`;
    }
}

function adjust(tID) {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            rpage();
        }
    }

    let bifield = document.getElementById(`adjustinput${tID}`);
    let adjustval = null;
    if(bifield.innerHTML == `<button onclick="docspage(${tID}, false)">Docs</button>`) {
        bifield.innerHTML = `<input type="number" id="adjustinput"/>`;
    } else {
        adjustval = document.getElementById("adjustinput").value;
        bifield.innerHTML =`<button onclick="docspage(${tID}, false)">Docs</button>`;
    }
    if(adjustval != null) {
        xhttp.open("POST",`http://localhost:8080/TRMS/adjust.do`);
        xhttp.setRequestHeader('Content-Type', 'application/json');
        if(adjustval > 0) {
            adjustval *= -1;
        }
        let tr = {
            tID : tID,
            fiscalYear : 0,
            empID : userL.empID,
            amount : adjustval,
            Status : 0
        }
        console.log(tr);
        xhttp.send(JSON.stringify(tr));
    }

}

function rejecttransaction(t_id) {
    let modal = document.getElementById("rejectmodal");
    modal.style.display = "block";
    selectedT = t_id;
}

function addRejection() {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            selectedT = null;
            cancelRejection();//just to reset the modal, bad name for it
            rpage();
        }
    }

    let tr = {
        tID : selectedT,
        fiscalYear : 0,
        empID : userL.empID,
        amount : 0,
        Status : 9

    }
    let mes = {
        message : document.getElementById("rejectionreason").value,
        transaction : tr
    }
    xhttp.open("POST",`http://localhost:8080/TRMS/reject.do`);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    console.log(mes);
    console.log(tr);
    xhttp.send(JSON.stringify(mes));


}

function cancelRejection() {
    let modal = document.getElementById("rejectmodal");
    document.getElementById("rejectionreason").value = "Comment on deletion";
    modal.style.display = "none";
    selectedT = null;
}

function getPendingPage() {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            document.getElementById("display").innerHTML = this.responseText;
        }
    }
    xhttp.open("GET", "http://localhost:8080/TRMS/getpendingpage.do");
    xhttp.send();

}

function loadpendingpage() {
    livepage = 1;
    getPendingPage();
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            let transactions = JSON.parse(this.responseText);
            populate(transactions, false);
        }
    }
    xhttp.open("POST",`http://localhost:8080/TRMS/getsubpending.do`);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(JSON.stringify(userL));
}

function loaddeptpage() {
    livepage = 2;
    getPendingPage();
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            let transactions = JSON.parse(this.responseText);
            populate(transactions, false);
        }
    }
    xhttp.open("POST",`http://localhost:8080/TRMS/getdeptpending.do`);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(JSON.stringify(userL));
}

function loadbencopage() {
    livepage = 3
    getPendingPage();
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            let transactions = JSON.parse(this.responseText);
            populate(transactions, true);
        }
    }
    xhttp.open("POST",`http://localhost:8080/TRMS/getbencopending.do`);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(JSON.stringify(userL));
}

function calcbal() {
    let bal = document.getElementById("amount").value;
    let percentage = 0;
    let year = document.getElementById("year").value;
    let avail = document.getElementById(`b${year}`).innerHTML;
    if(avail < 0) {
        avail = 0;
    }
    console.log(bal + " " + year + " " + avail);
    if(document.getElementById("radioUni").checked) {
        console.log("ruCh");
        percentage = document.getElementById("radioUni").value / 100;
    }
    if(document.getElementById("radioSeminar").checked) {
        console.log("ruS");
        percentage = document.getElementById("radioSeminar").value / 100;
    }
    if(document.getElementById("radioCertPrep").checked) {
        console.log("ruCP");
        percentage = document.getElementById("radioCertPrep").value / 100;
    }
    if(document.getElementById("radioCert").checked) {
        console.log("ruC");
        percentage = document.getElementById("radioCert").value / 100;
    }
    if(document.getElementById("radioTech").checked) {
        console.log("ruT");
        percentage = document.getElementById("radioTech").value / 100;
    }
    if(document.getElementById("radioOther").checked) {
        console.log("ruO");
        percentage = document.getElementById("radioOther").value / 100;
    }
    bal *= percentage;
    if(avail >= bal) {
        document.getElementById("balance").value = bal;
    } else {
        document.getElementById("balance").value = avail;
    }

}

function createtransaction() {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {

            loadrequestpage();
            
        }
    }
    let fy = document.getElementById("year").value;
    console.log("fy: " + fy);
	let emp = userL.empID;
    let a = document.getElementById("balance").value;
    console.log("amount: " + a);
    
    if(a > 0) {
        a *= -1;
    }
    sd = document.getElementById("date").value
    console.log(sd);
    if(!sd) {
        sd = "1999-12-31";
    }
    console.log("adjusted: " + a);
    let transaction = {
        tID : 0,
        fiscalYear : fy,
        empID : emp,
        amount : a,
        Status : -1,
        startdate : sd,
        location : document.getElementById("location").value,
        gradetype : document.getElementById("gradetype").value
    }
    console.log(JSON.stringify(transaction));
    
    xhttp.open("POST",`http://localhost:8080/TRMS/createt.do`);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(JSON.stringify(transaction));

}

function rpage() {
    switch(livepage) {
        case 0:
            loadrequestpage();
            break;
        case 1:
            loadpendingpage();
            break;
        case 2:
            loaddeptpage();
            break;
        case 3:
            loadbencopage();
            break;
        case 4:
            docspage(ltran, lwrit);
            break;
        case 5:
            getNewYearForm();
            break;
    }
}

function deletetransaction(t) {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            rpage();
        }
    }
    let transaction = {
        tID : t,
        fiscalYear : 0,
        empID : 0,
        amount : 0,
        Status : 0
    }
    xhttp.open("POST",`http://localhost:8080/TRMS/deletet.do`);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(JSON.stringify(transaction));

}

function approve(t) {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            rpage();
        }
    }

    let tr = {
        tID : t,
        fiscalYear : 0,
        empID : userL.empID,
        amount : 0,
        Status : 0
    }
    xhttp.open("POST",`http://localhost:8080/TRMS/approve.do`);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(JSON.stringify(tr));
}

ltran = null;
lwrit = null;
function docspage(tid, write) {
    getPendingPage();
    ltran = tid;
    lwrit = write;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            livepage = 4;
            document.getElementById("push").innerHTML =`<h3 id=tID>${tid}</h3><hr><label for="url">Document URL</label><input type="text" id="url"><button onclick="adddoc(${tid})">Add</button><hr>`
            let docs = JSON.parse(this.responseText);
            document.getElementById("pending").innerHTML = `<tr><td>URL</td><td>Action</td></tr>`
            for (const doc of docs) {
                if(write) {
                    document.getElementById("pending").innerHTML += `<tr><td>${doc}</td><td><button onclick="deletedoc('${doc}')">Delete</button></td></tr>`
                } else {
                    document.getElementById("pending").innerHTML += `<tr><td>${doc}</td><td><button disabled>Delete</button></td></tr>`
                }
                
            }
        }
    }
    transaction = {
        tID: tid,
        fiscalYear : 0,
        empID : 0,
        amount : 0,
        Status : 0
    }
    xhttp.open("POST",`http://localhost:8080/TRMS/getdocs.do`);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(JSON.stringify(transaction));
}

function adddoc(t) {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            rpage();
        }
    }
    xhttp.open("POST",`http://localhost:8080/TRMS/createdoc.do`);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    doc = {
        tID : t,
        url : document.getElementById("url").value
    }
    
    xhttp.send(JSON.stringify(doc));
}

function deletedoc(doc){
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            rpage();
        }
    }
    xhttp.open("POST",`http://localhost:8080/TRMS/deletedoc.do`);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(JSON.stringify(doc));
}

function getNewYearForm() {
    livepage = 5;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            document.getElementById("display").innerHTML = this.responseText;
        }
    }
    xhttp.open("GET", "http://localhost:8080/TRMS/getyearpage.do");
    xhttp.send();


}

function setUpYear() {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            rpage();
        }
    }
    let fy = document.getElementById("fy").value;
    let a = document.getElementById("amount").value;
    let transaction = {
        tID: 0,
        fiscalYear : fy,
        empID : 0,
        amount : a,
        Status : 5
    }
    xhttp.open("POST",`http://localhost:8080/TRMS/addtransactiontoall.do`);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(JSON.stringify(transaction));


}

/*
let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if(this.onreadystatechange == 4 && this.status == 200) {
            
        }
    }
*/
