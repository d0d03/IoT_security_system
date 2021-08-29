const statsReducer = (state,action) =>{
    switch(action.type){
        case 'GET_STATS':
            return action.statistics;
        default:
            return state;
    }
}

export {statsReducer as default}