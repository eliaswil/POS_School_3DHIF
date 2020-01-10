package at.htlkaindorf.exa_q2_205_game2048.bl;

public enum ColorScheme {
    C0(0, 0xffe8dfc1, 0xff000000),
    C2(2,0xfff7e1bc,0xff000000),
    C4(4,0xffdbd1ad,0xff000000),
    C8(8,0xfffcc16d,0xff000000),
    C16(16,0xfff7a22a,0xff000000),
    C32(32,0xfff7792a,0xff000000),
    C64(64, 0xff7612a, 0xffffffff),
    C128(128, 0xfffcd883, 0xffffffff),
    C256(256, 0xffedaf1c, 0xffffffff),
    C512(512, 0xffedaf1c, 0xffffffff),
    C1024(1024, 0xffedaf1c, 0xffffffff),
    C2048(2048, 0xffedaf1c, 0xffffffff);


    ColorScheme(int value, int backgroundColor, int fontColor){
        this.value = value;
        this.backgroundColor = backgroundColor;
        this.fontColor = fontColor;
    }

    private int value;
    private int backgroundColor;
    private int fontColor;
}
