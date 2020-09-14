export const parseHeader = (header: string) => {
    if (header.length === 0) {
        throw new Error('input must not be of zero length');
    }
    // Split parts by comma
    const parts = header.split(',');
    const links = {};
    // Parse each part into a named link
    parts.forEach((p) => {
        const section = p.split(';');
        if (section.length !== 2) {
            throw new Error('section could not be split on ";"');
        }
        const url = section[0].replace(/<(.*)>/, '$1').trim();
        const queryString = {page: 0};
        url.replace(new RegExp('([^?=&]+)(=([^&]*))?', 'g'), function ($0, $1, $2, $3) { return queryString[$1] = $3; });
        let page = queryString.page;
        if (typeof page === 'string') {
            page = parseInt(page, 10);
        }
        const name = section[1].replace(/rel="(.*)"/, '$1').trim();
        links[name] = page;
    });
    return links;
};
