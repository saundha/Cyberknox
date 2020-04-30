function changeAction(card) {
	if (card == 'debit') {
		document.getElementById('debit-info').style.display='block';
	}
	if (card == 'credit') {
		document.getElementById('credit-info').style.display='block';
	}
}

function show() {
	if (card == 'debit') {
		document.getElementById('debit-info').style.display='none';
	}
	if (card == 'credit') {
		document.getElementById('credit-info').style.display='none';
	}
}	

function interac(action) {
	if (action == 'open') {
		document.getElementById('interac-popup').style.display='flex';
	}
	if (action == 'close') {
		document.getElementById('interac-popup').style.display='none';
	}
}

function depositFunds(action) {
	if (action == 'open') {
		document.getElementById('deposit-popup').style.display='flex';
	}
	if (action == 'close') {
		document.getElementById('deposit-popup').style.display='none';
	}	
}

function withdrawFunds(action) {
	if (action == 'open') {
		document.getElementById('withdraw-popup').style.display='flex';
	}
	if (action == 'close') {
		document.getElementById('withdraw-popup').style.display='none';
	}	
}
