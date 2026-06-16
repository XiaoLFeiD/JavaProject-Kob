// import $ from 'jquery'
export default {
    state: {
        status: "matching", // matching表示匹配界面，playing表示对战界面
        socket: null, // socket链接
        opponent_username: "", //对手信息
        opponent_photo: "",
        gamemap: null, //地图信息
        a_id: 0,
        a_sx: 0,
        a_sy: 0,
        b_id: 0,
        b_sx: 0,
        b_sy: 0,
        gameObject: null,
        loser: "none", // none、all、A、B
    },
    getters: {},
    mutations: {
        updateSocket(state, socket) {
            state.socket = socket;
        },
        updateStatus(state, status) {
            state.status = status;
        },
        updateOpponent(state, opponent) {
            state.opponent_photo = opponent.photo;
            state.opponent_username = opponent.username;
        },
        updateGame(state, game) {
            state.gamemap = game.map;
            state.a_id = game.a_id;
            state.a_sx = game.a_sx;
            state.a_sy = game.a_sy;
            state.b_id = game.b_id;
            state.b_sx = game.b_sx;
            state.b_sy = game.b_sy;
        },
        updateGameObject(state, gameObject) {
            state.gameObject = gameObject;
        },
        updateLoser(state, loser) {
            state.loser = loser;
        }
    },
    actions: {},
    modules: {}
}