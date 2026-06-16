//方块向右移动 实际是方块一秒钟刷新60次 每一次渲染的画面称为帧 
//方块向右移动 也就是在一秒中渲染出60帧(每秒钟画60次) 得到每一时刻对应帧的位置 
//到了下一帧时刻就将上一帧画面删除 就造成物体移动的效果
//requestAnimationFrame(step) 是在下一帧浏览器渲染前执行一遍step回调函数
//而回调函数step使得浏览器在下次重绘之前更新动画

const AC_GAME_OBJECTS = [];

export class AcGameObject {
    constructor() {
        AC_GAME_OBJECTS.push(this);
        this.timedelta = 0; //执行一帧所用的时间
        this.has_called_start = false; //标记是否开始渲染
    }

    start() { // 只执行一次
    }

    update() { // 每一帧执行一次，除了第一帧之外

    }

    on_destroy() { // 删除之前执行

    }

    destroy() {
        this.on_destroy();

        for (let i in AC_GAME_OBJECTS) {
            const obj = AC_GAME_OBJECTS[i];
            if (obj === this) {
                AC_GAME_OBJECTS.splice(i);
                break;
            }
        }
    }
}

let last_timestamp; // 上一次执行的时刻
const step = timestamp => {
    for (let obj of AC_GAME_OBJECTS) {
        if (!obj.has_called_start) {
            obj.has_called_start = true;
            obj.start();
        } else {
            obj.timedelta = timestamp - last_timestamp;
            obj.update();
        }
    }

    last_timestamp = timestamp;
    requestAnimationFrame(step)
}

requestAnimationFrame(step)