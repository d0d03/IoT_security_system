const motionsReducer = (state,action) => {
    switch(action.type){
        case 'POPULATE_MOTIONS':
            return action.motions;
        default:
            return state;
    }
}

export { motionsReducer as default }