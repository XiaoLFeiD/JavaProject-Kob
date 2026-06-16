export class Cell {
    //每一个格子需要画的圆 x,y对应的是圆的中心
    constructor(r, c) {
        this.r = r;
        this.c = c;
        this.x = c + 0.5;
        this.y = r + 0.5;
    }
}