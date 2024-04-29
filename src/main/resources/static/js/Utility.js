export function hash(str) {
    const prime = 31
    const maxHashValue = 10000000000
    return str.split("").reduce((hash, char, index) => {
        const hashValue = hash + char.charCodeAt(0) * Math.pow(prime, index)
        return hashValue % maxHashValue // Limiting hash value within 10 billion
    }, 0)
}

export function invalidInput(cat, link, url) {
    if (cat === "") {
        return "Please enter a category."
    }
    if (link === "") {
        return "Please enter a link."
    }
    if (!isValidURL(url)) {
        return "Please enter a valid URL."
    }
    return false
}

function isValidURL(url) {
    const urlRegex = /^(https?:\/\/)?(www\.)?[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,4}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)$/
    return urlRegex.test(url)
}