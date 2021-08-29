import { backRoute, frontRoute } from '../constants';

function updateOptions(options){
    const update = {...options}
    update.headers = {
        ...update.headers,
        'Content-Type' : 'application/json'
    }
    if(localStorage.token){
        update.headers = {
            ...update.headers,
        };
    }
    return update;
}

export default async function fetcher(url,options){
    const response = await fetch(backRoute.concat(url),updateOptions(options));
    if(response.status === 200){
        return response.json();
    }else {
        console.log(response.json());
    }
}