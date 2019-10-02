Slooow.singlePlayerState = function(game) {
}

Slooow.singlePlayerState.prototype = {

	// Solo calculos de pantalla, pero se puede meter animacion para cargar los assets
		
	// Solo se ejecuta una vez, se pasa a preload (funcion), las funciones se van a guardar en un mapa (global)
	init : function() {
		if (game.global.DEBUG_MODE) {
			console.log("[DEBUG] Entering **SINGLEPLAYER** state");
		}
	},

	preload : function() {
		game.global.player = game.add.image(game.world.centerX, game.world.centerY, 'seaSnail')
		game.global.player.anchor.setTo(0.5, 0.5);
		game.global.player.scale.setTo(0.3, 0.3)
	},

	create : function() {
		this.wKey = game.input.keyboard.addKey(Phaser.Keyboard.W);
		game.input.keyboard.addKeyCapture([ Phaser.Keyboard.W]);

		//var suelo = new Phaser.Rectangle (30, 550, 30, 500)

		var graphics = game.add.graphics(100, 100);
		console.log('Dibujar rectangulo');
		graphics.lineStyle(2, 0x0000FF, 1);
    	//graphics.drawRect(50, 250, 500, 100);
	},

	// Se ejecuta siempre hasta que se consigue conexion, en ese caso, pasa a preload (escena)
	update : function() {

		let msg = {
			event: 'UPDATEINPUT',
			isStopping: false,
			useObject: false
		}

		if (this.wKey.isDown){
			msg.isStopping = true;
		} else if (this.wKey.isUp){
			
		}

		if (game.global.ground != null){
			graphics.drawRect(game.global.ground.x, game.global.ground.y, game.global.ground.heigth, game.global.ground.width)
		}

		game.global.socket.send(JSON.stringify(msg))
	}
}