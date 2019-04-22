S = 19;
W = 45;
H = 20;
CARDS_S = 105;
CARDS_N = 4;
NOISE = 1 - .05;
BUTTON_W = 105;
BUTTON_H = 40;

message = "stopped";
board = [];
buttons = [];
cards = [];
chosen = [];
canvas = 0;
ctx = 0;
speeds = [1000, 500,100,50,10];
speedsnames = ["very slow", "slow", "medium", "fast","very fast"];
speedindex = 0;

pos = {"x":0, "y":0};
end = {"x":0, "y":0};
dir = "east";
started = false;

img1 = new Image;
img1.src = "north.png";
img1.onload = function(){
	img2 = new Image;
	img2.src = "south.png";
	img2.onload = function(){
		img3 = new Image;
		img3.src = "west.png";
		img3.onload = function(){
			img4 = new Image;
			img4.src = "east.png";
			img4.onload = function(){
				img5 = new Image;
				img5.src = "forward.png";
				img5.onload = function(){
					img6 = new Image;
					img6.src = "backward.png";
					img6.onload = function(){
						img7 = new Image;
						img7.src = "turnc.png";
						img7.onload = function(){
							img8 = new Image;
							img8.src = "turnac.png";
							img8.onload = function(){
								imgb1 = new Image;
								imgb1.src = "start.png";
								imgb1.onload = function(){
									imgb2 = new Image;
									imgb2.src = "stop.png";
									imgb2.onload = function(){
										imgb3 = new Image;
										imgb3.src = "speed.png";
										imgb3.onload = function(){
											init();
										};
									};
								};
							};
						};
					};
				};
			};
		};
	};
};

seed = Math.random();

function init(){
	
	Math.seedrandom(seed);
	noise.seed(Math.random());

	// init board
	for(var x=0; x<W; x++) {
		board[x] = [];
		for(var y=0; y<H; y++) {
			var b = noise.simplex2(x*S/100, y*S/100);
			board[x][y] = (b > .3 && Math.random() < NOISE) || Math.random() > NOISE;
		}
	}
	pos.x = 1+Math.floor( Math.random() * W / 3);
	pos.y = 1+Math.floor( Math.random() * H / 3);
	end.x = W - pos.x;
	end.y = H - pos.y;
	//console.log(board);
	
	makeCards();
	makeButtons();
	
	//init canvas
	canvas = document.getElementById("board");
	ctx = canvas.getContext("2d");
	canvas.width  = S * W > CARDS_S * CARDS_N ? S * W : CARDS_S * CARDS_N;
	canvas.height = S * H + BUTTON_H + CARDS_S * 2;
	//canvas.addEventListener('mousedown', mousedown);
	canvas.addEventListener('mouseup', mouseup);
	
	draw();
}

function draw(){
	ctx.fillStyle = "#FFF";	
	ctx.fillRect(0,0,canvas.width, canvas.height);
	drawBoard(0, 0);
	drawButtons(0, S*H);
	drawCards(0, S*H + BUTTON_H);
}

function drawBoard(dx, dy){
	ctx.fillStyle = "#000";	
	for(var y=0; y<H; y++) {
		for(var x=0; x<W; x++) {
			if(board[x][y]){
				ctx.fillRect(dx + S * x, dy + S * y, S, S);
			}
		}
	}
	ctx.fillStyle = "#0F0";	
	ctx.fillRect(pos.x*S, pos.y*S, S, S);
	ctx.fillStyle = "#00F";	
	ctx.fillRect(end.x*S, end.y*S, S, S);
}

function drawButtons(dx, dy){
	for(var i=0; i<buttons.length; i++) {
		ctx.drawImage(buttons[i].image, dx+BUTTON_W*i, dy+5);
	}
	ctx.font = "20px Georgia";
	ctx.fillText(message, buttons.length * BUTTON_W, dy + BUTTON_H / 1.5);
}

function drawCards(dx, dy){
	for(var i=0; i<chosen.length; i++) {
		ctx.drawImage(cards[chosen[i]].image, dx + CARDS_S * i, dy);
	}
	for(var i=0; i<cards.length; i++) {
		ctx.drawImage(cards[i].image, dx + CARDS_S * i, dy + CARDS_S);
	}
}

/*function mousedown(e){
    var rect = canvas.getBoundingClientRect();
	var mx = e.clientX - rect.left;
	var my = e.clientY - rect.left;
}*/

function mouseup(e){
    var rect = canvas.getBoundingClientRect();
	var mx = e.clientX - rect.left;
	var my = e.clientY - rect.left;
	if(my > S*H + BUTTON_H + CARDS_S ){
		if(chosen.length < 8 && !started){
			i = Math.floor(mx / CARDS_S);
			chosen.push(i);
			draw();
		}
		return;
	}
	if(my > S*H + BUTTON_H){
		if(chosen.length <= 8 && !started){
			i = Math.floor(mx / CARDS_S);
			chosen.splice(i,1);
			draw();
		}
		return;
	}
	if(my>S*H){
		i = Math.floor(mx / BUTTON_W);
		buttons[i].action();
		return;
	}
}

async function startmoving(){
	started = true;
	var i = 0;
	var j = 0;
	while(started){
		
		cards[chosen[i]].action();
		
		if(pos.x == end.x && pos.y == end.y){
			stop();
			message = "WIN!";
		}
		
		message = "turn " + j;
		draw();
		
		i++;
		if(i >= chosen.length){
			i = 0;
			j++;
		}
		await sleep(speeds[speedindex]);
	}
}

function start(){
	if(!started && chosen.length > 0){
		message = "started";
		startmoving();
	}
}
function stop(){
	if(started){
		message = "stopped";
		init();
		started = false;
		draw();
	}
}
function speed(){
	speedindex++;
	if(speedindex >= speeds.length){
		speedindex = 0;
	}
	message = "speed: " + speedsnames[speedindex];
	draw();
}
function makeButtons(){
	buttons = [
		{
			"image": imgb1,
			"action": start
		},
		{
			"image": imgb2,
			"action": stop
		},
		{
			"image": imgb3,
			"action": speed
		}
	];
}
function makeCards(){
	cards = [
		{	// NORTH
			"id": 0,
			"image": img1,
			"action": function(){
				move(0, -1);
			}
		},
		{	// SOUTH
			"id": 1,
			"image": img2,
			"action": function(){
				move(0, 1);
			}
		},
		{	// WEST
			"id": 2,
			"image": img3,
			"action": function(){
				move(-1, 0);
			}
		},
		{	// EAST
			"id": 3,
			"image": img4,
			"action": function(){
				move(1,0);
			}
		},
		{	// FORW
			"id": 4,
			"image": img5,
			"action": function(){
				switch(dir){
					case "north":
						cards[0].action();
						break;
					case "south":
						cards[1].action();
						break;
					case "west":
						cards[2].action();
						break;
					case "east":
						cards[3].action();
						break;
				}
			}
		},
		{	// BACKW
			"id": 5,
			"image": img6,
			"action": function(){
				switch(dir){
					case "south":
						cards[0].action();
						break;
					case "north":
						cards[1].action();
						break;
					case "east":
						cards[2].action();
						break;
					case "west":
						cards[3].action();
						break;
				}
			}
		},
		{	// TURN CLOCK
			"id": 6,
			"image": img7,
			"action": function(){
				switch(dir){
					case "north":
						dir = "east";
						break;
					case "south":
						dir = "west";
						break;
					case "west":
						dir = "north";
						break;
					case "east":
						dir = "south";
						break;
				}
			}
		},
		{	// TURN ANTI CLOCK
			"id": 7,
			"image": img8,
			"action": function(){
				switch(dir){
					case "north":
						dir = "west";
						break;
					case "south":
						dir = "east";
						break;
					case "west":
						dir = "south";
						break;
					case "east":
						dir = "north";
						break;
				}
			}
		}
	];
}

function move(dx, dy){
	var newx = pos.x + dx;
	var newy = pos.y + dy;
	if(
		newx < board.length && 
		newy < board[0].length &&
		newx >= 0 &&
		newy >= 0 &&
		!board[newx][newy]
	){
		pos.x = newx;
		pos.y = newy;
	}
}

function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}
