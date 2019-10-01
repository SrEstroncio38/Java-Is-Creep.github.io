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
	},

	create : function() {
		this.wKey = game.input.keyboard.addKey(Phaser.Keyboard.W);
		game.input.keyboard.addKeyCapture([ Phaser.Keyboard.W]);
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
		}


		game.global.socket.send(JSON.stringify(msg))
			//game.state.start('preloadState')
	}
}