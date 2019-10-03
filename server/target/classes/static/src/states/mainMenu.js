Slooow.mainMenuState = function(game) {
    var buttonStartSolo
}

Slooow.mainMenuState.prototype = {

    StartSolo : function() {
        if (game.global.DEBUG_MODE) {
            console.log("[DEBUG] Entering **MainMenu** state");
        }
    },

    preload : function() {
    },

    create : function() {

        var style = {
			font : "bold 40px Impact",
			fill : "#ffffff",
			align : "center"
		};

        var styleTitle = {
			font : "bold 200px Impact",
			fill : "#ffffff",
			align : "center"
		};
        //Background
        b = game.add.image (game.world.centerX, game.world.centerY, 'background')
		b.anchor.set (0.5, 0.5)
        b.scale.setTo (1.2,1.2)

        //Texto titulo
		textTitle = game.add.text(game.world.centerX ,
            50, 'SLOOOW', styleTitle)
        textTitle.anchor.set(0.5)
        textTitle.scale.setTo(0.5,0.5)
        
        //Boton empezar juego solo
		buttonStartSolo = game.add.button(game.world.centerX + 100,
            game.world.centerY + 100, 'seaSnail', actionOnClickStartSolo, this,
            0, 0, 0)
        buttonStartSolo.anchor.set(0.5)
        buttonStartSolo.scale.setTo(0.3, 0.3)

        //Texto empezar juego solo
		textButtonStartSolo = game.add.text(game.world.centerX + 100,
            game.world.centerY + 100, 'SOLO', style)
        textButtonStartSolo.anchor.set(0.5)
        textButtonStartSolo.scale.setTo(0.5,0.5)

        //Boton tienda
		buttonShop = game.add.button(game.world.centerX - 300,
            game.world.centerY , 'seaSnail', actionOnClickStartShop, this,
            0, 0, 0)
        buttonShop.anchor.set(0.5)
        buttonShop.scale.setTo(0.3, 0.3)

        //Texto tienda
		textButtonShop = game.add.text(game.world.centerX - 300,
            game.world.centerY , 'SHOP', style)
        textButtonShop.anchor.set(0.5)
        textButtonShop.scale.setTo(0.5,0.5)

        //Boton desconectar
		buttonDisconnect = game.add.button(40,
            40, 'seaSnail', actionOnClickDisconnect, this,
            0, 0, 0)
        buttonDisconnect.anchor.set(0.5)
        buttonDisconnect.scale.setTo(0.3, 0.3)

        //Texto desconectar
		textButtonDisconnect = game.add.text(40,
            40, 'Disconnect', style)
        textButtonDisconnect.anchor.set(0.5)
        textButtonDisconnect.scale.setTo(0.5,0.5)
        
        function actionOnClickStartSolo(){
            let msg = {
                event: 'SINGLEPLAYER',
                playerName: game.global.username,
                roomName: 'sala1'
            }
            game.global.socket.send(JSON.stringify(msg))
            game.state.start('singlePlayerState')
        }

        function actionOnClickDisconnect(){
            game.global.username = ''
            game.global.password = ''
            let msg = {
                event: 'DISCONNECT',
                playerName: game.global.username,
            }
            game.global.socket.send(JSON.stringify(msg))
            game.state.start('initSesionState')
        }

        function actionOnClickStartShop(){
            game.state.start('shopState')
        }

    },

    update : function() {
    }
}