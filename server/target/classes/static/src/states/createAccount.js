Slooow.createAccountState = function(game) {
    var b
}

Slooow.createAccountState.prototype = {

    init : function() {
        if (game.global.DEBUG_MODE) {
            console.log("[DEBUG] Entering **CREATEACCOUNT** state");
        }
    },

    preload : function() {
        //Background
        b = game.add.image (game.world.centerX, game.world.centerY, 'background')
		b.anchor.set (0.5, 0.5)
        b.scale.setTo (1.2,1.2)
        
        //Campo nombre de usuario
        usernameButton = game.add.inputField(game.world.centerX - 160,
            game.world.centerY - 100, {
            font: '18px Arial',
            fill: '#212121',
            fontWeight: 'bold',
            height: 30,
            width: 300,
            padding: 8,
            borderWidth: 1,
            borderColor: '#000',
            borderRadius: 6,
            placeHolder: 'Username'
        });

        // Campo PassWord
        passwordButton = game.add.inputField(game.world.centerX - 160,
            game.world.centerY - 30, {
            font: '18px Arial',
            fill: '#212121',
            fontWeight: 'bold',
            height: 30,
            width: 300,
            padding: 8,
            borderWidth: 1,
            borderColor: '#000',
            borderRadius: 6,
            placeHolder: 'Password',
            type: PhaserInput.InputType.password
        });

        //Campo confirmar pass
        confirmPasswordButton = game.add.inputField(game.world.centerX - 160,
            game.world.centerY +40, {
            font: '18px Arial',
            fill: '#212121',
            fontWeight: 'bold',
            height: 30,
            width: 300,
            padding: 8,
            borderWidth: 1,
            borderColor: '#000',
            borderRadius: 6,
            placeHolder: 'Password',
            type: PhaserInput.InputType.password
        });
    },

    create : function() {
    },

    update : function() {
    }
}