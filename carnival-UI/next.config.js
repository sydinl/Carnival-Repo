module.exports =  () => {
  const rewrites = () => {
    return [
      {
        source: "/iflow/:path*",
        destination: "http://localhost:8080/:path*",
      },
    ];
  };
  return {
    rewrites,
  };
};
