/*var config = {
    type: Phaser.AUTO,
    width: 800,
    height: 600,
    physics: {
        default: 'arcade',
        arcade: {
            gravity: { y: 200 }
        }
    },
    scene: {
        preload: preload,
        create: create
    }
};*/

var game = new Phaser.Game(1024, 600, Phaser.AUTO, 'gameDiv');

function preload ()
{
    this.load.setBaseURL('http://labs.phaser.io');

    this.load.image('sky', 'assets/skies/space3.png');
    this.load.image('logo', 'assets/sprites/phaser3-logo.png');
    this.load.image('red', 'assets/particles/red.png');
    
}

function create ()
{
    this.add.image(400, 300, 'sky');
    var connection = new WebSocket('ws://127.0.0.1:8080/snail');
    connection.onopen = () => {
    
        console.log('[DEBUG] WebSocket connection opened.')
    
    }

    connection.onmessage = (event)=> {
        console.log('[DEBUG] WebSocket message received: ' + event.data);
    }

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

    var sendMessageButton = this.add.text(100, 100, 'Enviar mensaje!', { fill: '#0f0' });
    sendMessageButton.setInteractive();

    sendMessageButton.on('pointerdown',()=>{
        let msg = new Object()
    msg.event = 'DEBUG'
    msg.message = 'este es un mensaje de prueba'
    msg.remitente = 'cliente'
    msg.datos = [3,8,16]
    msg.intruso = 'axwell'
        connection.send(JSON.stringify(msg));
    })
}