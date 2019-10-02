var game;

window.onload = function () {
    game = new Phaser.Game(1024, 600, Phaser.AUTO, 'gameDiv');
    console.log('Despues cargar juego');

    //Variables globales compartidas entre escenas
    game.global = {
        //Socket
        socket: null,
        FPS: 60,
        DEBUG_MODE: true,
        player : null,
        mapObjects: [],
        mapDrawn: false
    }
    console.log('Despues crear game global');

    game.global.socket = new WebSocket('ws://127.0.0.1:8080/snail');
    game.global.socket.onopen = () => {

        console.log('[DEBUG] WebSocket connection opened.')

    }

    game.global.socket.onclose = () => {
        console.log('[DEBUG] Websocket connection closed');
    }

    game.global.socket.onmessage = (message) => {
        var msg = JSON.parse(message.data)
        console.log(msg);

        switch (msg.event) {
            
            case 'TICK':
                if (game.global.DEBUG_MODE) {
                    console.log('[DEBUG] TICK message recieved')
                    console.dir(msg)
                }
                game.global.player.x = Math.floor(msg.posX)
                game.global.player.y = Math.floor(msg.posY) +500
                break

            case 'DRAWMAP':
                var array =  JSON.parse(msg.mapObjects)
                var i = 0;
                for (var object in array){
                    game.global.mapObjects[i].x = object.posX;
                    game.global.mapObjects[i].y = object.posY +500
                    game.global.mapObjects[i].height = object.height;
                    game.global.mapObjects[i].width = object.width;
                    i++;
                }
                break;
                
                    
        }
    }

    this.game.state.add('bootState', Slooow.bootState);
    this.game.state.add('preloadState', Slooow.preloadState);
    this.game.state.add('initSesionState', Slooow.initSesionState);
    this.game.state.add('createAccountState', Slooow.createAccountState);
    this.game.state.add('mainMenuState', Slooow.mainMenuState);
    this.game.state.add('singlePlayerState', Slooow.singlePlayerState);
    this.game.state.add('shopState', Slooow.shopState);

    this.game.state.start('bootState');
    /*
    function create() {
        console.log('Estado create');
        //this.add.image(400, 300, 'sky');

        /*
            var particles = this.add.particles('red');
        
            var emitter = particles.createEmitter({
                speed: 100,
                scale: { start: 1, end: 0 },
                blendMode: 'ADD'
            });
        
            var logo = this.physics.add.image(400, 100, 'logo');
        
            logo.setVelocity(100, 200);
            logo.setBounce(1, 1);
            logo.setCollideWorldBounds(true);
        
            emitter.startFollow(logo);
        */
    /*
     var sendMessageButton = this.add.text(100, 100, 'Enviar mensaje!', { fill: '#0f0' });
     sendMessageButton.setInteractive();
 
    button = game.add.button(game.world.centerX - 95, 460, 'button', openWindow, this, 2, 1, 0);
    button.input.useHandCursor = true;

    button.on('pointerdown', () => {
        let msg = new Object()
        msg.event = 'DEBUG'
        msg.message = 'este es un mensaje de prueba'
        msg.remitente = 'cliente'
        msg.datos = [3, 8, 16]
        msg.intruso = 'axwell'
        connection.send(JSON.stringify(msg));
    })
}*/
}