let lineBar = null;
let stopLineBar = false;

$(function () {
    lineBar = new ProgressBar.Line('#line-container', {
        color: '#FCB03C',
        strokeWidth: 3,
        easing: 'linear',
        text: {
            className: 'progressbar__label',
            style: {
                position: 'absolute',
                left: '50%',
                top: '50%',
                padding: 0,
                width: '300px',
                fontSize: '10.5pt',
                marginTop: '-20px',
                transform: {
                    prefix: true,
                    value: 'translate(-50%, -50%)'
                }
            }
        }
    });

    function loop() {
        if (!stopLineBar) {
            getChannelToken();
            lineBar.set(1);
            lineBar.animate(0, {
                duration: 15000
            }, loop);
        }
    }

    loop();
});