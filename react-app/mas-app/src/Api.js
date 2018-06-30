const getData = async (params) => {
    //const response = await fetch('/api/hello', { body: params });
    //const body = await response.json();
    //if (response.status !== 200) throw Error(body.message);

    let teste = { data: [{ name: 'r', data: [['2014', 100], ['2015', 71.5], ['2016', 106.4]] }] };
    return await teste;
};

export default getData;
